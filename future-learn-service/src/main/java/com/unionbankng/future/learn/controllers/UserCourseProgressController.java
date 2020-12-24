package com.unionbankng.future.learn.controllers;

import com.unionbankng.future.learn.entities.Course;
import com.unionbankng.future.learn.entities.Lecture;
import com.unionbankng.future.learn.entities.UserCourseProgress;
import com.unionbankng.future.learn.pojo.APIResponse;
import com.unionbankng.future.learn.pojo.CreateCourseRequest;
import com.unionbankng.future.learn.pojo.UserCourseProgressRequest;
import com.unionbankng.future.learn.services.UserCourseProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class UserCourseProgressController {


    private final UserCourseProgressService userCourseProgressService;

    @GetMapping("/v1/user_course_progress/get_my_progress")
    public ResponseEntity<APIResponse<UserCourseProgress>> getMyProgress(@ApiIgnore OAuth2Authentication authentication, @RequestParam Long courseId){

        UserCourseProgress userCourseProgress = userCourseProgressService.findMyCourseProgressByCourseId(courseId, authentication);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,userCourseProgress));
    }

    @PostMapping("/v1/user_course_progress/save_my_progress")
    public ResponseEntity<APIResponse<UserCourseProgress>> saveMyCourseProgress(@RequestBody @Valid UserCourseProgressRequest request,
                                                               @ApiIgnore OAuth2Authentication authentication){

        UserCourseProgress userCourseProgress = userCourseProgressService.computePercentageAndSaveProgress(request,authentication);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,userCourseProgress));
    }

}
