package com.unionbankng.future.learn.controllers;


import com.unionbankng.future.learn.entities.Announcement;
import com.unionbankng.future.learn.entities.LectureNote;
import com.unionbankng.future.learn.pojo.APIResponse;
import com.unionbankng.future.learn.pojo.CreateAnnouncementRequest;
import com.unionbankng.future.learn.services.AnnouncementService;
import com.unionbankng.future.learn.services.LectureNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class LectureNoteController {

    private final LectureNoteService lectureNoteService;


    @GetMapping("/v1/lecture_note/get_by_course_and_userUUID")
    public ResponseEntity<APIResponse<Page<LectureNote>>> getCourseAnnouncements(@RequestParam Long courseId,
                                                                                  @RequestParam String userUUID){

        List<LectureNote> lectureNotes = lectureNoteService.findAllByCourseIdAndUserUUID(courseId, userUUID);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,lectureNotes));
    }

    @PostMapping(value = "/v1/lecture_note/create_or_update")
    public ResponseEntity<APIResponse<Announcement>> createOrUpdateAnnouncement(@RequestBody CreateAnnouncementRequest request) {

        Announcement announcement = announcementService.createOrUpdateAnnouncement(request);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,announcement));
    }

    @DeleteMapping(value = "/v1/announcements/delete/{announcementId}")
    public ResponseEntity<APIResponse<String>> createOrUpdateAnnouncement(@PathVariable Long announcementId) {

        announcementService.deleteById(announcementId);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,"Announcement deleted successful"));
    }
}
