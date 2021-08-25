package com.unionbankng.future.learn.controllers;


import com.unionbankng.future.learn.entities.Course;
import com.unionbankng.future.learn.entities.UserCourse;
import com.unionbankng.future.learn.pojo.APIResponse;
import com.unionbankng.future.learn.pojo.CourseEnrollmentRequest;
import com.unionbankng.future.learn.services.UserCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<APIResponse<List<Course>>> getMyCourses(@ApiIgnore Principal principal){

        List<Course> courses = userCourseService.getMyCourses(principal);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,courses));
    }

    @PostMapping(value = "/v1/user_course/enroll")
    public ResponseEntity<APIResponse<UserCourse>> enrollForCourse(@RequestBody CourseEnrollmentRequest request
            ,@ApiIgnore Principal principal) {

        UserCourse userCourse = userCourseService.enrollForFreeCourse(request,principal);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,userCourse));
    }

    @GetMapping("/v1/user_course/check_enrollment/{courseId}")
    public ResponseEntity<APIResponse<Boolean>> isUserEnrolled(@ApiIgnore Principal principal, @PathVariable Long courseId){

        Boolean enrolled = userCourseService.isUserEnrolledForCourse(principal,courseId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,enrolled));
    }
}
