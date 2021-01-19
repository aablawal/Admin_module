package com.unionbankng.future.learn.controllers;


import com.unionbankng.future.learn.entities.Question;
import com.unionbankng.future.learn.pojo.APIResponse;
import com.unionbankng.future.learn.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class QuestionsController {

    private final QuestionService questionService;


    @GetMapping("/v1/questions/find_by_lecture_id")
    public ResponseEntity<APIResponse<List<Question>>> findAllByLectureId(@RequestParam Long lectureId){

        List<Question> questions = questionService.findAllByLectureId(lectureId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,questions));
    }

    @DeleteMapping("/v1/questions/delete_by_id/{questionId}")
    public ResponseEntity<APIResponse<String>> deleteById(@PathVariable Long questionId){

        questionService.deleteById(questionId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,"Request successful"));
    }

    @GetMapping("/v1/questions/find_by_id/{questionId}")
    public ResponseEntity<APIResponse<Question>> findAllByCreatorUUID(@PathVariable Long questionId){

        Question question = questionService.findById(questionId).orElseThrow(()
                ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Question Not Found"));

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,question));
    }



    @PostMapping("/v1/questions/create_new")
    public ResponseEntity<APIResponse<Question>> creatContent( @RequestBody Question request)  {

        Question question = questionService.save(request);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,question));
    }


}
