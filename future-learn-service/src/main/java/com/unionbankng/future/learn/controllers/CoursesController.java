package com.unionbankng.future.learn.controllers;


import com.unionbankng.future.learn.entities.Course;
import com.unionbankng.future.learn.entities.Instructor;
import com.unionbankng.future.learn.pojo.APIResponse;
import com.unionbankng.future.learn.pojo.CreateCourseRequest;
import com.unionbankng.future.learn.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class CoursesController {

    private final CourseService courseService;


    @GetMapping("/v1/courses/get_by_instructor")
    public ResponseEntity<APIResponse<Page<Course>>> getCoursesByInstructor(@RequestParam String instructorUUID
    , @RequestParam int page, @RequestParam int size){

        Page<Course> courses = courseService.findAllByInstructorsIn(instructorUUID, PageRequest.of(page,size));

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,courses));
    }

    @GetMapping("/v1/courses/get_by_creator")
    public ResponseEntity<APIResponse<Page<Course>>> getCoursesByCreator(@RequestParam String creatorUUID
            , @RequestParam int page, @RequestParam int size){

        Page<Course> courses = courseService.findAllByCreatorUUID(creatorUUID, PageRequest.of(page,size));

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,courses));
    }

    @GetMapping("/v1/courses")
    public ResponseEntity<APIResponse<Page<Course>>> getCoursesByInstructor(@RequestParam int page, @RequestParam int size){

        Page<Course> courses = courseService.findAllByIsPublished(true,PageRequest.of(page,size));

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,courses));
    }



    @PostMapping("/v1/courses/create_course")
    public ResponseEntity<APIResponse<Course>> createCourseAPI(@RequestBody CreateCourseRequest request){

        Course course = courseService.createCourse(request);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,course));
    }

    @PostMapping(value = "/v1/courses/{courseId}/upload_course_img",consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse<Course>> uploadCourseImage(@Nullable @RequestPart("image") MultipartFile image,
                                                                @PathVariable Long courseId) throws IOException {

        Course course = courseService.updateCourseImg(image,courseId);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,course));
    }

    @GetMapping("/user/info")
    public Object getUserInfo(OAuth2Authentication authentication) {
        Map<String, String> map = new Hashtable<String, String>();
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        return details.getDecodedDetails();
    }


}
