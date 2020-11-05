package com.unionbankng.future.learn.controllers;


import com.unionbankng.future.learn.entities.CourseContent;
import com.unionbankng.future.learn.entities.Lecture;
import com.unionbankng.future.learn.enums.LectureType;
import com.unionbankng.future.learn.pojo.APIResponse;
import com.unionbankng.future.learn.pojo.CourseContentRequest;
import com.unionbankng.future.learn.pojo.CreateLectureRequest;
import com.unionbankng.future.learn.services.CourseContentService;
import com.unionbankng.future.learn.services.LectureService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class LecturesController {

    private final LectureService lectureService;


    @GetMapping("/v1/lectures/find_by_course_content")
    public ResponseEntity<APIResponse<List<Lecture>>> findAllByCourseContentId(@RequestParam Long courseContentId){

        List<Lecture> lectures = lectureService.findAllByCourseContentId(courseContentId, Sort.by("index"));

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,lectures));
    }

    @GetMapping("/v1/lectures/find_by_course_id")
    public ResponseEntity<APIResponse<List<Lecture>>> findAllByCourseId(@RequestParam Long courseId){

        List<Lecture> lectures = lectureService.findAllByCourseId(courseId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,lectures));
    }

    @GetMapping("/v1/lectures/find_by_creatorUUID")
    public ResponseEntity<APIResponse<Page<Lecture>>> findAllByCreatorUUID(@RequestParam String creatorUUID, @RequestParam int page
    ,@RequestParam int size){

        Page<Lecture> lectures = lectureService.findAllByCreatorUUID(creatorUUID,PageRequest.of(page,size));

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,lectures));
    }

    @GetMapping("/v1/lectures/generate_streaming_links")
    public ResponseEntity<APIResponse<String>> generateStreamingLinks(@RequestParam Long lectureId){


        String links = lectureService.generateStreamingLinks(lectureId);

        if(links == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong when generating links");

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,links));
    }



    @PostMapping(value = "/v1/lectures/create_new_lecture", consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse<Lecture>> creatContent(@Nullable @RequestPart("file") MultipartFile file,
                                                                   @RequestBody @Valid CreateLectureRequest request) throws IOException {

        Lecture lecture = lectureService.createNewLecture(file,request);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,lecture));
    }




}
