package com.unionbankng.future.learn.repositories;

import com.unionbankng.future.learn.entities.Course;
import com.unionbankng.future.learn.entities.EmbeddedCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmbeddedCourseRepository extends JpaRepository<EmbeddedCourse,Long> {
    Page<Course> findAllByCreatorUUID(String creatorUUID, Pageable pageable);
    Page<Course> findAllByIsPublished(Boolean isPublished, Pageable pageable);
}
