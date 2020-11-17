package com.unionbankng.future.learn.repositories;

import com.unionbankng.future.learn.entities.CourseQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseQuestionRepository extends JpaRepository<CourseQuestion,Long> {

    Page<CourseQuestion> findAllByCourseId(Long courseId, Pageable pageable);
    Page<CourseQuestion> findAllByLectureId(Long lectureId, Pageable pageable);
    Long countAllByCourseId(Long courseId);
    Long countAllByLectureId(Long lectureId);

}
