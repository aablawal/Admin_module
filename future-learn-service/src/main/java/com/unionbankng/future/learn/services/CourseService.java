package com.unionbankng.future.learn.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import com.unionbankng.future.learn.entities.Course;
import com.unionbankng.future.learn.entities.EmbeddedCourse;
import com.unionbankng.future.learn.entities.EmbeddedCourseLecture;
import com.unionbankng.future.learn.entities.Instructor;
import com.unionbankng.future.learn.pojo.APIResponse;
import com.unionbankng.future.learn.pojo.CreateCourseRequest;
import com.unionbankng.future.learn.pojo.JwtUserDetail;
import com.unionbankng.future.learn.repositories.CourseRepository;
import com.unionbankng.future.learn.repositories.EmbeddedCourseLectureRepository;
import com.unionbankng.future.learn.repositories.EmbeddedCourseRepository;
import com.unionbankng.future.learn.repositories.InstructorRepository;
import com.unionbankng.future.learn.util.JWTUserDetailsExtractor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    private final EmbeddedCourseRepository embeddedCourseRepository;
    private final EmbeddedCourseLectureRepository embeddedCourseLectureRepository;
    Logger logger = LoggerFactory.getLogger(CourseService.class);


    public Course save(Course course){
        return courseRepository.save(course);
    }

    public Page<Course> findAllWhereIamCreator(Principal principal, Pageable pageable){
        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        return findAllByCreatorUUID(jwtUserDetail.getUserUUID(),pageable);
    }

    public Page<Course> findAllByCreatorUUID(String creatorUUID, Pageable pageable){
        return courseRepository.findAllByCreatorUUID(creatorUUID,pageable);
    }

    public Page<Course> findAllByIsPublished(Boolean isPublished, Pageable pageable){
        return courseRepository.findAllByIsPublished(isPublished,pageable);
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

    public Page<Course> findAllWhereIamInstructor(Principal principal, Pageable pageable){
        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        System.out.println(jwtUserDetail.getUserUUID());
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

    public Course createCourse(CreateCourseRequest request,Principal principal){

        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);

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

        course.setCourseTitle(request.getCourseTitle());
        course.setCreatorUUID(creatorUUID);
        course.setEstimatedTimeToComplete(request.getEstimatedTimeToComplete());
        course.setInstructors(instructors);
        course.setDescription(request.getDescription());
        course.setShortDesc(request.getShortDesc());
        course.setIsPaid(request.getIsPaid());
        course.setIsPublished(request.getIsPublished());
        course.setOutcomes(request.getOutcomes());
        course.setRequirements(request.getRequirements());
        course.setPrice(request.getPrice());

        return save(course);

    }//create course


    //create course from URL
    public APIResponse createEmbeddedCourse(String  courseData,  String lecturesData, Principal principal) {
        try {
            JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
            EmbeddedCourse course = new ObjectMapper().readValue(courseData, EmbeddedCourse.class);
            EmbeddedCourseLecture[] lectures = new ObjectMapper().readValue(lecturesData, EmbeddedCourseLecture[].class);

            course.setCreatorUUID(currentUser.getUserUUID());
            course.setIsPaid(course.getPaymentOption()==null?false:true);
            course.setIsPublished(true);

            EmbeddedCourse savedCourse = embeddedCourseRepository.save(course);
            if (savedCourse != null) {
                int i=0;
                for (EmbeddedCourseLecture lecture : lectures) {
                    i++;
                    lecture.setCreatorUUID(currentUser.getUserUUID());
                    lecture.setIndexNo(i);
                    lecture.setIsPublished(true);
                    lecture.setCourseId(savedCourse.getId());

                    logger.info(new ObjectMapper().writeValueAsString(lecture));
                    embeddedCourseLectureRepository.save(lecture);
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
    public APIResponse<Map<String, Object>> findEmbeddedCourseById(Long id){
        Map<String,Object> courseData=new HashMap<>();
        EmbeddedCourse course= embeddedCourseRepository.findById(id).orElse(null);
        if(course!=null) {
            List<EmbeddedCourseLecture> lectures = embeddedCourseLectureRepository.findAllByCourseId(id);
            courseData.put("course",course);
            courseData.put("lectures",lectures);
            return   new APIResponse("success", true, courseData);
        }else{
            return new APIResponse("Course not found", false, null);
        }
    }
    public APIResponse<ArrayList<Map<String, Object>>> findEmbeddedCourses(){
        ArrayList<Map<String,Object>> baseList=new ArrayList<>();
        List<EmbeddedCourse> courses= embeddedCourseRepository.findAll();
        for (EmbeddedCourse course: courses) {
            Map<String,Object> courseData=new HashMap<>();
            if(course!=null) {
                List<EmbeddedCourseLecture> lectures = embeddedCourseLectureRepository.findAllByCourseId(course.getId());
                courseData.put("course", course);
                courseData.put("lectures", lectures);
                baseList.add(courseData);
            }
        }
        return   new APIResponse("success", true, baseList);
    }
    public void publishCourse(Long courseId, Principal principal) {

        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        Course course = courseRepository.findById(courseId).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found"));

        List<String> UUIDs = course.getInstructors().stream().map(i -> i.getInstructorUUID()).collect(Collectors.toList());
        UUIDs.add(course.getCreatorUUID());

        if(!UUIDs.contains(jwtUserDetail.getUserUUID()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only Instructors and the course creator can publish a course");

        course.setIsPublished(true);
        save(course);
    }

    public Course updateCourse(Long courseId, CreateCourseRequest request,Principal principal){
        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
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
