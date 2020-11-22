package com.unionbankng.future.learn.services;

import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import com.unionbankng.future.learn.entities.Course;
import com.unionbankng.future.learn.entities.Instructor;
import com.unionbankng.future.learn.pojo.CreateCourseRequest;
import com.unionbankng.future.learn.pojo.JwtUserDetail;
import com.unionbankng.future.learn.repositories.CourseRepository;
import com.unionbankng.future.learn.repositories.InstructorRepository;
import com.unionbankng.future.learn.util.JWTUserDetailsExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final FileStorageService fileStorageService;

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
    public Course updateCourseImg(MultipartFile image , @ParameterValueKeyProvider Long courseId,String ext) throws IOException {

        Course course = courseRepository.findById(courseId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found"));
        if(course.getCourseImg() != null)
            fileStorageService.deleteFileFromStorage(course.getCourseImg(), BlobType.IMAGE);

        String source = fileStorageService.storeFile(image,courseId,BlobType.IMAGE,ext);
        course.setCourseImg(source);
        return courseRepository.save(course);
    }

    public Course createCourse(CreateCourseRequest request, OAuth2Authentication authentication){

        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);

        return createCourse(request, jwtUserDetail.getUserUUID());


    }//create course


    public Course createCourse(CreateCourseRequest request, String creatorUUID){

        Course course = new Course();
        ArrayList<Instructor> instructors = new ArrayList<>();

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


    public void publishCourse(Long courseId, OAuth2Authentication authentication) {

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
}
