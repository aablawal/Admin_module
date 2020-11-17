package com.unionbankng.future.learn.controllers;


import com.unionbankng.future.learn.entities.CourseQuestion;
import com.unionbankng.future.learn.pojo.APIResponse;
import com.unionbankng.future.learn.pojo.PublishCourseQuestionRequest;
import com.unionbankng.future.learn.services.CourseQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class CourseQuestionController {

    private final CourseQuestionService courseQuestionService;


    @GetMapping("/v1/course_question/get_for_course")
    public ResponseEntity<APIResponse<Page<CourseQuestion>>> getQandAQuestionsForCourses(@RequestParam Long courseId,
                                                                                         @RequestParam int page, @RequestParam int size){

        Page<CourseQuestion> courseQuestions = courseQuestionService.findAllByCourseId(courseId, PageRequest.of(page,size
        , Sort.by("createdAt")));

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,courseQuestions));
    }

    @GetMapping("/v1/course_question/get_for_lecture")
    public ResponseEntity<APIResponse<Page<CourseQuestion>>> getQandAQuestionsForLecture(@RequestParam Long lectureId,
                                                                                         @RequestParam int page, @RequestParam int size){

        Page<CourseQuestion> courseQuestions = courseQuestionService.findAllByLectureId(lectureId, PageRequest.of(page,size
                , Sort.by("createdAt")));

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,courseQuestions));
    }

    @PostMapping(value = "/v1/course_question/publish")
    public ResponseEntity<APIResponse<CourseQuestion>> publishQuestion(@RequestBody PublishCourseQuestionRequest request
    , @ApiIgnore OAuth2Authentication authentication) {

        CourseQuestion courseQuestion = courseQuestionService.publishCourseQuestion(request,authentication);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true, courseQuestion));
    }

}
