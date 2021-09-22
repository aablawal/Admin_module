package com.unionbankng.future.learn.controllers;


import com.unionbankng.future.learn.entities.Course;
import com.unionbankng.future.learn.entities.UserCourse;
import com.unionbankng.future.learn.pojo.APIResponse;
import com.unionbankng.future.learn.pojo.CourseEnrollmentRequest;
import com.unionbankng.future.learn.services.UserCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class UserCourseController {

    private final UserCourseService userCourseService;


    @GetMapping("/v1/user_course/get_my_courses")
    public ResponseEntity<APIResponse<List<Course>>> getMyCourses(@ApiIgnore OAuth2Authentication authentication){

        List<Course> courses = userCourseService.getMyCourses(authentication);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,courses));
    }

    @PostMapping(value = "/v1/user_course/enroll")
    public ResponseEntity<APIResponse<UserCourse>> enrollForCourse(@RequestBody CourseEnrollmentRequest request
            ,@ApiIgnore OAuth2Authentication authentication) {

        UserCourse userCourse = userCourseService.enrollForFreeCourse(request,authentication);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,userCourse));
    }

    @GetMapping("/v1/user_course/check_enrollment/{courseId}")
    public ResponseEntity<APIResponse<Boolean>> isUserEnrolled(@ApiIgnore OAuth2Authentication authentication, @PathVariable Long courseId){

        Boolean enrolled = userCourseService.isUserEnrolledForCourse(authentication,courseId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,enrolled));
    }
}
