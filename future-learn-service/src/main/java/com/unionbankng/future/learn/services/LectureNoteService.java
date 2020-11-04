package com.unionbankng.future.learn.services;

import com.unionbankng.future.learn.entities.LectureNote;
import com.unionbankng.future.learn.repositories.LectureNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureNoteService {

    private final LectureNoteRepository lectureNoteRepository;

    public LectureNote save(LectureNote lectureNote){
        return lectureNoteRepository.save(lectureNote);
    }

    public List<LectureNote> findAllByCourseIdAndUserUUID(Long courseId, String userUUID){
        return lectureNoteRepository.findAllByCourseIdAndUserUUID(courseId,userUUID);
    }

    public List<LectureNote> findAllByLectureIdAndUserUUID(Long lectureId, String userUUID){
        return lectureNoteRepository.findAllByLectureIdAndUserUUID(lectureId,userUUID);
    }

    public void deleteById(Long lectureNoteId){
        lectureNoteRepository.deleteById(lectureNoteId);
    }



}
