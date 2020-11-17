package com.unionbankng.future.learn.repositories;

import com.unionbankng.future.learn.entities.CourseQuestionComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseQuestionCommentRepository extends JpaRepository<CourseQuestionComment,Long> {

    Page<CourseQuestionComment> findAllByCourseQuestionId(Long courseQuestionId, Pageable pageable);
    Long countAllByCourseQuestionId(Long courseQuestionId);

}
