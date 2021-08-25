package com.unionbankng.future.learn.repositories;

import com.unionbankng.future.learn.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {

    List<Question> findAllByLectureId(Long lectureId);
}
