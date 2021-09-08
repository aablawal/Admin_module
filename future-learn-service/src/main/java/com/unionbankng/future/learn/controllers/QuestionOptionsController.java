package com.unionbankng.future.learn.controllers;


import com.unionbankng.future.learn.entities.Question;
import com.unionbankng.future.learn.entities.QuestionOption;
import com.unionbankng.future.learn.pojo.APIResponse;
import com.unionbankng.future.learn.services.QuestionOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class QuestionOptionsController {

    private final QuestionOptionService questionOptionService;


    @DeleteMapping("/v1/questions/delete_by_id/{questionOptionId}")
    public ResponseEntity<APIResponse<String>> deleteById(@PathVariable Long questionOptionId){

        questionOptionService.deleteById(questionOptionId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,"Request successful"));
    }





    @PostMapping("/v1/question_options/add_to_question/{questionId}")
    public ResponseEntity<APIResponse<Question>> addToQuestion(@PathVariable Long questionId ,@RequestBody QuestionOption request)  {

        Question question = questionOptionService.addToQuestion(questionId,request);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,question));
    }


}
