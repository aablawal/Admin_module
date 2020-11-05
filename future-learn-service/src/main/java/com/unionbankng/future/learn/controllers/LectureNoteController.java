package com.unionbankng.future.learn.controllers;


import com.unionbankng.future.learn.entities.Announcement;
import com.unionbankng.future.learn.entities.Lecture;
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
    public ResponseEntity<APIResponse<Page<LectureNote>>> findAllByCourseIdAndUserUUID(@RequestParam Long courseId,
                                                                                  @RequestParam String userUUID){

        List<LectureNote> lectureNotes = lectureNoteService.findAllByCourseIdAndUserUUID(courseId, userUUID);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,lectureNotes));
    }

    @GetMapping(value = "/v1/lecture_note/get_by_lecture_and_userUUID")
    public ResponseEntity<APIResponse<List<LectureNote>>> findAllByLectureIdAndUserUUID(@RequestParam Long lectureId,
                                                                                     @RequestParam String userUUID) {

        List<LectureNote> lectureNotes = lectureNoteService.findAllByLectureIdAndUserUUID(lectureId,userUUID);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,lectureNotes));
    }

    @DeleteMapping(value = "/v1/lecture_note/delete/{lectureNoteId}")
    public ResponseEntity<APIResponse<String>> deleteNote(@PathVariable Long lectureNoteId) {

        lectureNoteService.deleteById(lectureNoteId);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,"Note deleted successful"));
    }

    @PostMapping(value = "/v1/lecture_note")
    public ResponseEntity<APIResponse<LectureNote>> createNote(@RequestBody LectureNote request) {

        LectureNote lectureNote = lectureNoteService.save(request);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,lectureNote));
    }


    @PutMapping(value = "/v1/lecture_note")
    public ResponseEntity<APIResponse<LectureNote>> updateNote(@RequestBody LectureNote request) {

        LectureNote lectureNote = lectureNoteService.save(request);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,lectureNote));
    }
}
