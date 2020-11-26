package com.unionbankng.future.learn;

import com.unionbankng.future.learn.entities.Question;
import com.unionbankng.future.learn.entities.QuestionOption;
import com.unionbankng.future.learn.services.QuestionOptionService;
import com.unionbankng.future.learn.services.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionOptionServiceTest extends AbstractTest{

    @Autowired
    QuestionOptionService questionOptionService;

    @Autowired
    QuestionService questionService;


    @Test
    public void addOptionsToQuestionTest() {

        Question q = new Question();
        q.setLectureId(2l);
        q.setIndexNo(0);
        q.setAnswerIndex(0);
        q.setQuestionText("What is your name ?");

        q = questionService.save(q);

       QuestionOption questionOption = new QuestionOption();
       questionOption.setOptionText("John");
       questionOption.setIndexNo(0);

       q = questionOptionService.addToQuestion(q.getId(),questionOption);

        assertEquals(1,q.getOptions().size());


    }


}
