package com.unionbankng.future.learn.controllers;


import com.unionbankng.future.learn.entities.Course;
import com.unionbankng.future.learn.entities.UserCourse;
import com.unionbankng.future.learn.pojo.APIResponse;
import com.unionbankng.future.learn.pojo.CourseEnrollmentRequest;
import com.unionbankng.future.learn.pojo.CreateCourseRequest;
import com.unionbankng.future.learn.services.CourseService;
import com.unionbankng.future.learn.services.UserCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class UserCourseController {

    private final UserCourseService userCourseService;


    @GetMapping("/v1/user_course/get_my_courses")
    public ResponseEntity<APIResponse<List<Course>>> getMyCourses(@RequestParam String userUUID){

        List<Course> courses = userCourseService.getMyCourses(userUUID);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,courses));
    }

    @PostMapping(value = "/v1/user_course/enroll")
    public ResponseEntity<APIResponse<UserCourse>> enrollForCourse(@RequestBody CourseEnrollmentRequest request) throws IOException {

        UserCourse userCourse = userCourseService.enrollForCourse(request);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,userCourse));
    }
}