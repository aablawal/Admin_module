package com.unionbankng.future.learn;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.futureutilityservice.grpcserver.StreamingLocatorResponse;
import com.unionbankng.future.learn.entities.Lecture;
import com.unionbankng.future.learn.entities.Question;
import com.unionbankng.future.learn.entities.QuestionOption;
import com.unionbankng.future.learn.enums.LectureType;
import com.unionbankng.future.learn.pojo.CreateLectureRequest;
import com.unionbankng.future.learn.services.FutureStreamingService;
import com.unionbankng.future.learn.services.LectureService;
import com.unionbankng.future.learn.services.QuestionOptionService;
import com.unionbankng.future.learn.services.QuestionService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QuestionOptionServiceTest extends AbstractTest{

    @Autowired
    QuestionOptionService questionOptionService;

    @Autowired
    QuestionService questionService;


    @Test
    public void addOptionsToQuestionTest() {

        Question q = new Question();
        q.setLectureId(2l);
        q.setIndex(0);
        q.setAnswerIndex(0);
        q.setQuestionText("What is your name ?");

        q = questionService.save(q);

       QuestionOption questionOption = new QuestionOption();
       questionOption.setOptionText("John");
       questionOption.setIndex(0);

       q = questionOptionService.addToQuestion(q.getId(),questionOption);

        assertEquals(1,q.getOptions().size());


    }


}
