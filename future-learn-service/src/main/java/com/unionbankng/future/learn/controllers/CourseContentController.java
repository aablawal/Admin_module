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
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class CourseContentController {

    private final CourseContentService courseContentService;


    @GetMapping("/v1/course_content/find_by_course_id")
    public ResponseEntity<APIResponse<List<CourseContent>>> findAllByCourseId(@RequestParam Long courseId){

        List<CourseContent> courseContents = courseContentService.findAllByCourseId(courseId, Sort.by("indexNo"));

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,courseContents));
    }


    @GetMapping("/v1/course_content/find_where_iam_creator")
    public ResponseEntity<APIResponse<List<CourseContent>>> findWhereIAmIsCreator(@ApiIgnore Principal principal,
                                                                                 @RequestParam int page, @RequestParam int size){

        Page<CourseContent> courseContents = courseContentService.findAllByCreatorUUID(principal, PageRequest.of(page,size));

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,courseContents));
    }


    @PostMapping("/v1/course_content/create_content")
    public ResponseEntity<APIResponse<CourseContent>> createContent(@RequestBody CourseContentRequest request,
                                                                   @ApiIgnore Principal principal){

        CourseContent courseContent = courseContentService.createNewContent(request,principal);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,courseContent));
    }

    @PostMapping("/v1/course_content/update_content")
    public ResponseEntity<APIResponse<CourseContent>> updateContent(@RequestBody CourseContent request){

        CourseContent courseContent = courseContentService.updateCourseContent(request);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,courseContent));
    }

    @DeleteMapping("/v1/course_content/{courseContentId}")
    public ResponseEntity<APIResponse<String>> deleteCourseContent(@PathVariable Long courseContentId){

        courseContentService.deleteById(courseContentId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,"Content deleted successfully"));
    }


}
