package com.unionbankng.future.learn.controllers;


import com.unionbankng.future.learn.entities.CourseContent;
import com.unionbankng.future.learn.pojo.APIResponse;
import com.unionbankng.future.learn.pojo.CourseContentRequest;
import com.unionbankng.future.learn.services.CourseContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class CourseContentController {

    private final CourseContentService courseContentService;


    @GetMapping("/v1/course_content/find_by_course_id")
    public ResponseEntity<APIResponse<List<CourseContent>>> findAllByCourseId(@RequestParam Long courseId){

        List<CourseContent> courseContents = courseContentService.findAllByCourseId(courseId, Sort.by("index"));

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,courseContents));
    }

    @GetMapping("/v1/course_content/find_by_creator_id")
    public ResponseEntity<APIResponse<List<CourseContent>>> findAllByCreatorUUID(@RequestParam String creatorUUID,
                                                                                 @RequestParam int page, @RequestParam int size){

        Page<CourseContent> courseContents = courseContentService.findAllByCreatorUUID(creatorUUID, PageRequest.of(page,size));

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,courseContents));
    }


    @PostMapping("/v1/course_content/create_content")
    public ResponseEntity<APIResponse<CourseContent>> creatContent(@RequestBody CourseContentRequest request){

        CourseContent courseContent = courseContentService.createNewContent(request);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,courseContent));
    }


}
