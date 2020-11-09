package com.unionbankng.future.learn.controllers;


import com.unionbankng.future.learn.entities.Announcement;
import com.unionbankng.future.learn.entities.Course;
import com.unionbankng.future.learn.entities.UserCourse;
import com.unionbankng.future.learn.pojo.APIResponse;
import com.unionbankng.future.learn.pojo.CourseEnrollmentRequest;
import com.unionbankng.future.learn.pojo.CreateAnnouncementRequest;
import com.unionbankng.future.learn.services.AnnouncementService;
import com.unionbankng.future.learn.services.UserCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class AnnouncementsController {

    private final AnnouncementService announcementService;


    @GetMapping("/v1/announcements/get_by_course")
    public ResponseEntity<APIResponse<Page<Announcement>>> getCourseAnnouncements(@RequestParam Long courseId,
                                                                                  @RequestParam int page, @RequestParam int size){

        Page<Announcement> announcements = announcementService.findAllByCourseId(courseId, PageRequest.of(page,size
        , Sort.by("createdAt")));

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,announcements));
    }

    @PostMapping(value = "/v1/announcements/create_or_update")
    public ResponseEntity<APIResponse<Announcement>> createOrUpdateAnnouncement(@RequestBody CreateAnnouncementRequest request
    ,@ApiIgnore OAuth2Authentication authentication) {

        Announcement announcement = announcementService.createOrUpdateAnnouncement(request,authentication);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,announcement));
    }

    @DeleteMapping(value = "/v1/announcements/delete/{announcementId}")
    public ResponseEntity<APIResponse<String>> createOrUpdateAnnouncement(@PathVariable Long announcementId) {

        announcementService.deleteById(announcementId);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,"Announcement deleted successful"));
    }
}
