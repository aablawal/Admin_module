package com.unionbankng.future.learn.repositories;

import com.unionbankng.future.learn.entities.LectureNote;
import com.unionbankng.future.learn.entities.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface LectureNoteRepository extends JpaRepository<LectureNote,Long> {

    List<LectureNote> findAllByCourseIdAndUserUUID(Long courseId, String userUUID);
    List<LectureNote> findAllByLectureIdAndUserUUID(Long lectureId, String userUUID);
}
