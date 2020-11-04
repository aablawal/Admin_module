package com.unionbankng.future.learn.repositories;

import com.unionbankng.future.learn.entities.Course;
import com.unionbankng.future.learn.entities.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {

    Page<Course> findAllByCreatorUUID(String creatorUUID, Pageable pageable);
    Page<Course> findAllByIsPublished(Boolean isPublished, Pageable pageable);
    List<Course> findAllByIdIn(List<Long> ids);
    @Query(value = "SELECT c FROM Course c where :instructor MEMBER OF c.instructors")
    Page<Course> findAllByInstructorsIn(Instructor instructor, Pageable pageable);
}
