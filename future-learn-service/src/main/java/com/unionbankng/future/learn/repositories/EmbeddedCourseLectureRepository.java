package com.unionbankng.future.learn.repositories;

import com.unionbankng.future.learn.entities.Course;
import com.unionbankng.future.learn.entities.EmbeddedCourseLecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmbeddedCourseLectureRepository extends JpaRepository<EmbeddedCourseLecture,Long> {
    Page<Course> findAllByCreatorUUID(String creatorUUID, Pageable pageable);
    Page<Course> findAllByIsPublished(Boolean isPublished, Pageable pageable);
    List<EmbeddedCourseLecture> findAllByCourseId(Long id);
}
