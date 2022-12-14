package com.unionbankng.future.learn.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import com.unionbankng.future.learn.entities.*;
import com.unionbankng.future.learn.enums.CourseType;
import com.unionbankng.future.learn.enums.LectureType;
import com.unionbankng.future.learn.pojo.*;
import com.unionbankng.future.learn.repositories.*;
import com.unionbankng.future.learn.util.JWTUserDetailsExtractor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final FileStorageService fileStorageService;
    private final LectureRepository lectureRepository;
    private final CourseContentRepository courseContentRepository;
    Logger logger = LoggerFactory.getLogger(CourseService.class);


    public Course save(Course course){
        return courseRepository.save(course);
    }

    public Page<Course> findAllWhereIamCreator(OAuth2Authentication authentication, Pageable pageable){
        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
        return findAllByCreatorUUID(jwtUserDetail.getUserUUID(),pageable);
    }

    public Page<Course> findAllByCreatorUUID(String creatorUUID, Pageable pageable){
        return courseRepository.findAllByCreatorUUID(creatorUUID,pageable);
    }

    public Page<Course> findAllByIsPublished(Boolean isPublished, Pageable pageable){
        return courseRepository.findAllByIsPublished(isPublished,pageable);
    }
    public Page<Course> findAllByCategory(Long categoryId, Pageable pageable){
        return courseRepository.findAllByCategory(categoryId,pageable);
    }

    public Long getTotalCourseCreatedByUserUUID(String  userUUID){
        return  courseRepository.getTotalCourseCreatedByUserUUID(userUUID).orElse(0l);
    }

    public Optional<Course> findById(Long courseId){
        return courseRepository.findById(courseId);
    }

    public List<Course> findAllByIdIn (List<Long> ids){
        return courseRepository.findAllByIdIn(ids);
    }

    public Page<Course> findAllByInstructorsIn(Instructor instructor, Pageable pageable){
        return courseRepository.findAllByInstructorsIn(instructor,pageable);
    }

    public Page<Course> findAllWhereIamInstructor(OAuth2Authentication authentication, Pageable pageable){
        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
        Instructor instructor = instructorRepository.findByInstructorUUID(jwtUserDetail.getUserUUID())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found"));

        return findAllByInstructorsIn(instructor,pageable);
    }

    public Page<Course> findAllByInstructorsIn(String userUUID, Pageable pageable){
        Instructor instructor = instructorRepository.findByInstructorUUID(userUUID)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found"));

        return findAllByInstructorsIn(instructor,pageable);
    }

    @CachePut(value = "course", key="#courseId")
    public Course updateCourseImg(MultipartFile image , @ParameterValueKeyProvider Long courseId) throws IOException {

        Course course = courseRepository.findById(courseId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found"));
        if(course.getCourseImg() != null)
            fileStorageService.deleteFileFromStorage(course.getCourseImg(), BlobType.IMAGE);

        String source = fileStorageService.storeFile(image,courseId,BlobType.IMAGE);
        course.setCourseImg(source);
        return courseRepository.save(course);
    }

    public Course createCourse(CreateCourseRequest request,OAuth2Authentication authentication){

        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);

        return createCourse(request, jwtUserDetail.getUserUUID());

    }//create course


    public Course createCourse(CreateCourseRequest request, String creatorUUID){

        Course course = new Course();
        ArrayList<Instructor> instructors = new ArrayList<>();

        if(request.getIsPaid() && (request.getAccountNumber() == null || request.getAccountName() == null))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account number and name can not be empty when the course is paid");

        //instructor
        if(request.getInstructors() != null)
        for (Instructor i : request.getInstructors()){
            Instructor instructor = instructorRepository.findByInstructorUUID(i.getInstructorUUID()).orElse(new Instructor());
            instructor.setNumberOfCourses(instructor.getNumberOfCourses() == null ? 0: instructor.getNumberOfCourses() + 1);
            instructor.setInstructorUUID(i.getInstructorUUID());
            instructors.add(instructorRepository.save(instructor));
        }

        course.setType(CourseType.INTERNAL);
        course.setCourseTitle(request.getCourseTitle());
        course.setCreatorUUID(creatorUUID);
        course.setEstimatedTimeToComplete(request.getEstimatedTimeToComplete());
        course.setInstructors(instructors);
        course.setDescription(request.getDescription());
        course.setShortDesc(request.getShortDesc());
        course.setIsPaid(request.getIsPaid());
        course.setIsPublished(request.getIsPublished());
        course.setOutcomes(request.getOutcomes());
        course.setCategory(request.getCategory());
        course.setRequirements(request.getRequirements());
        course.setPrice(request.getPrice());

        return save(course);

    }//create course


    //create course from URL
    public APIResponse createEmbeddedCourse(String  courseData,  String lecturesData,OAuth2Authentication authentication) {
        try {
            JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
            EmbeddedCourse embeddedCourse = new ObjectMapper().readValue(courseData, EmbeddedCourse.class);
            EmbeddedCourseLecture[]  embeddedCourseLectures = new ObjectMapper().readValue(lecturesData, EmbeddedCourseLecture[].class);
            embeddedCourse.setCreatorUUID(currentUser.getUserUUID());
            embeddedCourse.setIsPaid(false);
            embeddedCourse.setIsPublished(true);

            //instances of the Sidekiq course objects
            Course course = new Course();
            ArrayList<Instructor> instructors = new ArrayList<>();

            //preparing instructor
            Instructor instructor = instructorRepository.findByInstructorUUID(currentUser.getUserUUID()).orElse(new Instructor());
            instructor.setNumberOfCourses(instructor.getNumberOfCourses() == null ? 0: instructor.getNumberOfCourses() + 1);
            instructor.setInstructorUUID(currentUser.getUserUUID());
            instructors.add(instructorRepository.save(instructor));

            //course details
            course.setType(CourseType.EXTERNAL);
            course.setCourseTitle(embeddedCourse.getTitle());
            course.setCreatorUUID(currentUser.getUserUUID());
            course.setEstimatedTimeToComplete(embeddedCourse.getDuration());
            course.setInstructors(instructors);
            course.setDescription(embeddedCourse.getFullDescription());
            course.setShortDesc(embeddedCourse.getDescription());
            course.setIsPaid(embeddedCourse.getIsPaid());
            course.setIsPublished(embeddedCourse.getIsPublished());
            course.setRequirements(embeddedCourse.getRequirements());
            course.setPrice(embeddedCourse.getPrice());
            course.setCategory(embeddedCourse.getCategory());
            course.setCourseImg(embeddedCourse.getCourseImg());

            Course savedCourse = courseRepository.save(course);
            if (savedCourse != null) {
                int i=0;
                for (EmbeddedCourseLecture embeddedCourseLecture: embeddedCourseLectures) {
                    i++;
                    embeddedCourseLecture.setCourseId(savedCourse.getId());
                    embeddedCourseLecture.setCreatorUUID(currentUser.getUserUUID());
                    embeddedCourseLecture.setIndexNo(i);
                    embeddedCourseLecture.setIsPublished(true);
                    embeddedCourseLecture.setCourseId(savedCourse.getId());

                    //create course content
                    CourseContent courseContent = new CourseContent();
                    courseContent.setCourseContentText(embeddedCourseLecture.getTitle());
                    courseContent.setCourseId(embeddedCourseLecture.getCourseId());
                    courseContent.setCreatorUUID(embeddedCourseLecture.getCreatorUUID());
                    courseContent.setIndexNo(embeddedCourseLecture.getIndexNo());
                    CourseContent savedCourseContent= courseContentRepository.save(courseContent);

                    //generate sidekiq lecture
                    Lecture lecture = new Lecture();
                    lecture.setCourseContentId(savedCourseContent.getId());
                    lecture.setCourseId(savedCourse.getId());
                    lecture.setCreatorUUID(embeddedCourseLecture.getCreatorUUID());
                    lecture.setDuration(embeddedCourseLecture.getDuration());
                    lecture.setOutputAssetName(embeddedCourse.getInstructor());
                    lecture.setStreamingLocatorName(embeddedCourseLecture.getUrl());
                    lecture.setIndexNo(embeddedCourseLecture.getIndexNo());
                    lecture.setTitle(embeddedCourseLecture.getTitle());
                    lecture.setType(LectureType.VIDEO);

                    lectureRepository.save(lecture);
                }
                return new APIResponse("success", true, savedCourse);

            } else {
                return new APIResponse("Unable to create course", false, null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse("Failed", false, ex.getMessage());
        }
    }
    public void publishCourse(Long courseId,OAuth2Authentication authentication) {

        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
        Course course = courseRepository.findById(courseId).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found"));

        List<String> UUIDs = course.getInstructors().stream().map(i -> i.getInstructorUUID()).collect(Collectors.toList());
        UUIDs.add(course.getCreatorUUID());

        if(!UUIDs.contains(jwtUserDetail.getUserUUID()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only Instructors and the course creator can publish a course");

        course.setIsPublished(true);
        save(course);
    }

    public Course updateCourse(Long courseId, CreateCourseRequest request,OAuth2Authentication authentication){
        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
        String userId= jwtUserDetail.getUserUUID();
        Course course = courseRepository.findById(courseId).orElse(null);
        if(course!=null) {
            course.setCourseTitle(request.getCourseTitle());
            course.setCreatorUUID(userId);
            course.setEstimatedTimeToComplete(request.getEstimatedTimeToComplete());
            course.setDescription(request.getDescription());
            course.setShortDesc(request.getShortDesc());
            course.setIsPaid(request.getIsPaid());
            course.setIsPublished(request.getIsPublished());
            course.setOutcomes(request.getOutcomes());
            course.setRequirements(request.getRequirements());
            course.setPrice(request.getPrice());
            return save(course);
        }else{
            return null;
        }
    }//update course

    public  Boolean deleteCourseById(Long courseId){
        courseRepository.deleteById(courseId);
        return  true;
    }//edit course
}
