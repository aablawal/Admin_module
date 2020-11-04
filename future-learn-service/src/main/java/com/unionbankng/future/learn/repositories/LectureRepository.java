package com.unionbankng.future.learn.repositories;

import com.unionbankng.future.learn.entities.Instructor;
import com.unionbankng.future.learn.entities.Lecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture,Long> {

    List<Lecture> findAllByCourseContentId(Long courseContentId, Sort sort);
    List<Lecture> findAllByCourseId(Long courseId);
    Page<Lecture> findAllByCreatorUUID(String creatorUUID, Pageable pageable);
}
