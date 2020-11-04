package com.unionbankng.future.learn.services;

import com.unionbankng.future.learn.entities.Question;
import com.unionbankng.future.learn.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Question save(Question question){
        return questionRepository.save(question);
    }

    public List<Question> findAllByLectureId(Long lectureId){
        return questionRepository.findAllByLectureId(lectureId);
    }

    public Optional<Question> findById(Long questionId){
        return questionRepository.findById(questionId);
    }

    public void deleteById(Long questionId){
         questionRepository.deleteById(questionId);
    }



}
