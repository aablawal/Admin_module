package com.unionbankng.future.learn.controllers;


import com.unionbankng.future.learn.entities.Announcement;
import com.unionbankng.future.learn.pojo.APIResponse;
import com.unionbankng.future.learn.pojo.CreateAnnouncementRequest;
import com.unionbankng.future.learn.services.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;

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
    ,@ApiIgnore Principal principal) {

        Announcement announcement = announcementService.createOrUpdateAnnouncement(request,principal);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,announcement));
    }

    @DeleteMapping(value = "/v1/announcements/delete/{announcementId}")
    public ResponseEntity<APIResponse<String>> createOrUpdateAnnouncement(@PathVariable Long announcementId) {

        announcementService.deleteById(announcementId);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,"Announcement deleted successful"));
    }
}
