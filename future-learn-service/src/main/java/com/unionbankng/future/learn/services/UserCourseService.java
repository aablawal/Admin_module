package com.unionbankng.future.learn.services;

import com.unionbankng.future.learn.entities.Course;
import com.unionbankng.future.learn.entities.UserCourse;
import com.unionbankng.future.learn.pojo.CourseEnrollmentRequest;
import com.unionbankng.future.learn.pojo.JwtUserDetail;
import com.unionbankng.future.learn.repositories.UserCourseRepository;
import com.unionbankng.future.learn.util.JWTUserDetailsExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCourseService {

    private final UserCourseRepository userCourseRepository;
    private final CourseService courseService;
    private final InstructorService instructorService;

    public UserCourse save(UserCourse userCourse){
        return userCourseRepository.save(userCourse);
    }

    public Page<UserCourse> findAllByCourseId(Long courseId, Pageable pageable){
        return userCourseRepository.findAllByCourseId(courseId,pageable);
    }

    public List<UserCourse> findAllByUserUUID(String userUUID){
        return userCourseRepository.findAllByUserUUID(userUUID);
    }

    public Boolean existByUserUUIDAndCourseId(String userUUID, Long courseId){
        return userCourseRepository.existsByUserUUIDAndCourseId(userUUID,courseId);
    }

    public Long countAllByCourseId(Long courseId){
        return userCourseRepository.countAllByCourseId(courseId);
    }

    public Long countAllByUserUUID(String userUUID){
        return userCourseRepository.countAllByUserUUID(userUUID);
    }

    public UserCourse enrollForCourse(CourseEnrollmentRequest courseEnrollmentRequest,OAuth2Authentication authentication){

        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);

        return enrollForCourse(courseEnrollmentRequest,jwtUserDetail.getUserUUID());

    }

    @Transactional
    public UserCourse enrollForCourse(CourseEnrollmentRequest courseEnrollmentRequest,String userUUID){

        if(existByUserUUIDAndCourseId(userUUID,courseEnrollmentRequest.getCourseEnrollingForId()))
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are already enrolled for this course");

        Course course = courseService.findById(courseEnrollmentRequest.getCourseEnrollingForId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found")
        );

        if(!course.getIsPublished())
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course not published");

        if(course.getIsPaid())
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment not implemented at the moment");

        // increase course instructor learner count
        course.getInstructors().forEach(i ->
            i.setNumberOfLearners(i.getNumberOfLearners() + 1));
        instructorService.saveAll(course.getInstructors());

        UserCourse userCourse = new UserCourse();
        userCourse.setCourseId(courseEnrollmentRequest.getCourseEnrollingForId());
        userCourse.setUserUUID(userUUID);

        return save(userCourse);

    }

    public List<Course> getMyCourses(OAuth2Authentication authentication){

        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);

        return getMyCourses(jwtUserDetail.getUserUUID());

    }

    public List<Course> getMyCourses(String myUUID){


        if(!userCourseRepository.existsByUserUUID(myUUID))
            return new ArrayList<>();

        List<Long> userCoursesIds = findAllByUserUUID(myUUID).stream().map(u -> u.getCourseId() ).collect(Collectors.toList());

        return courseService.findAllByIdIn(userCoursesIds);

    }

}
