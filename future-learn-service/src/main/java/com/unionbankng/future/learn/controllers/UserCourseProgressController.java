package com.unionbankng.future.learn.controllers;

import com.unionbankng.future.learn.entities.UserCourseProgress;
import com.unionbankng.future.learn.pojo.APIResponse;
import com.unionbankng.future.learn.pojo.UserCourseProgressRequest;
import com.unionbankng.future.learn.services.UserCourseProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class UserCourseProgressController {


    private final UserCourseProgressService userCourseProgressService;

    @GetMapping("/v1/user_course_progress/get_my_progress")
    public ResponseEntity<APIResponse<UserCourseProgress>> getMyProgress(@ApiIgnore Principal principal, @RequestParam Long courseId){

        UserCourseProgress userCourseProgress = userCourseProgressService.findMyCourseProgressByCourseId(courseId, principal);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,userCourseProgress));
    }

    @PostMapping("/v1/user_course_progress/save_my_progress")
    public ResponseEntity<APIResponse<UserCourseProgress>> saveMyCourseProgress(@RequestBody @Valid UserCourseProgressRequest request,
                                                               @ApiIgnore Principal principal){

        UserCourseProgress userCourseProgress = userCourseProgressService.computePercentageAndSaveProgress(request,principal);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,userCourseProgress));
    }

}
