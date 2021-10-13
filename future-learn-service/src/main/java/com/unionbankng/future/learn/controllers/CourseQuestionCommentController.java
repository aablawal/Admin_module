package com.unionbankng.future.learn.controllers;


import com.unionbankng.future.learn.entities.CourseQuestionComment;
import com.unionbankng.future.learn.pojo.APIResponse;
import com.unionbankng.future.learn.pojo.AddCourseQuestionCommentRequest;
import com.unionbankng.future.learn.services.CourseQuestionCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class CourseQuestionCommentController {

    private final CourseQuestionCommentService courseQuestionCommentService;


    @GetMapping("/v1/course_question_comment/get_for_question")
    public ResponseEntity<APIResponse<Page<CourseQuestionComment>>> getCommentsForQuestion(@RequestParam Long questionId,
                                                                                           @RequestParam int page, @RequestParam int size){

        Page<CourseQuestionComment> qandAComments = courseQuestionCommentService.findAllByCourseQuestionId(questionId, PageRequest.of(page,size
        , Sort.by("createdAt")));

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,qandAComments));
    }


    @PostMapping(value = "/v1/course_question_comment/post_question_comment")
    public ResponseEntity<APIResponse<CourseQuestionComment>> AddCommentToQuestion(@RequestBody AddCourseQuestionCommentRequest request
    , @ApiIgnore OAuth2Authentication authentication) {

        CourseQuestionComment courseQuestionComment = courseQuestionCommentService.addCommentToQuestion(request,authentication);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true, courseQuestionComment));
    }

}
