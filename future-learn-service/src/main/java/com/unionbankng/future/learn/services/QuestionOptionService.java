package com.unionbankng.future.learn.services;

import com.unionbankng.future.learn.entities.Question;
import com.unionbankng.future.learn.entities.QuestionOption;
import com.unionbankng.future.learn.repositories.QuestionOptionRepository;
import com.unionbankng.future.learn.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionOptionService {

    private final QuestionOptionRepository questionOptionRepository;
    private final QuestionRepository questionRepository;

    public QuestionOption save(QuestionOption questionOption){
        return questionOptionRepository.save(questionOption);
    }

    public Question addToQuestion(Long questionId,QuestionOption questionOption ){

        Question question = questionRepository.findById(questionId).orElseThrow(()
                ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Question Not Found"));
        question.getOptions().add(questionOption);

        questionRepository.save(question);

        return question;
    }

    public Optional<QuestionOption> findById(Long questionId){
        return questionOptionRepository.findById(questionId);
    }

    public void deleteById(Long questionOptionId){
        questionOptionRepository.deleteById(questionOptionId);
    }



}
