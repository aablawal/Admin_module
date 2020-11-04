package com.unionbankng.future.learn.repositories;

import com.unionbankng.future.learn.entities.CourseContent;
import com.unionbankng.future.learn.entities.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseContentRepository extends JpaRepository<CourseContent,Long> {

    List<CourseContent> findAllByCourseId(Long courseId, Sort sort);
    Page<CourseContent> findAllByCreatorUUID(String creatorUUID, Pageable pageable);
}
