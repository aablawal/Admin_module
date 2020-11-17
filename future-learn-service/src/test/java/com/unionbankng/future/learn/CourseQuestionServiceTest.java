package com.unionbankng.future.learn;

import com.unionbankng.future.learn.entities.CourseQuestion;
import com.unionbankng.future.learn.pojo.PublishCourseQuestionRequest;
import com.unionbankng.future.learn.services.CourseQuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CourseQuestionServiceTest extends AbstractTest{

    @Autowired
    CourseQuestionService courseQuestionService;



    @Test
    public void publishQandAQuestion() {

        PublishCourseQuestionRequest publishCourseQuestionRequest = new PublishCourseQuestionRequest();
        publishCourseQuestionRequest.setCourseId(1l);
        publishCourseQuestionRequest.setDescription("A very long description for test");
        publishCourseQuestionRequest.setLectureId(2l);
        publishCourseQuestionRequest.setTitle("A Question");


        CourseQuestion courseQuestion = courseQuestionService.publishCourseQuestion(publishCourseQuestionRequest,"12222-33333-333","Okeme Christian");

        assertEquals("12222-33333-333", courseQuestion.getCreatorUUID());


    }

    @Test
    public void findQuestionsByCourseIdTest() {

        PublishCourseQuestionRequest publishCourseQuestionRequest = new PublishCourseQuestionRequest();
        publishCourseQuestionRequest.setCourseId(1l);
        publishCourseQuestionRequest.setDescription("A very long description for test");
        publishCourseQuestionRequest.setLectureId(2l);
        publishCourseQuestionRequest.setTitle("A Question");

        courseQuestionService.publishCourseQuestion(publishCourseQuestionRequest,"12222-33333-333","Okeme Christian");


        Page<CourseQuestion> qandAQuestionPage = courseQuestionService.findAllByCourseId(1l, PageRequest.of(0,10
                , Sort.by("createdAt")));

        assertTrue(qandAQuestionPage.hasContent());


    }

    @Test
    public void findQuestionsByLectureIdTest() {

        PublishCourseQuestionRequest publishCourseQuestionRequest = new PublishCourseQuestionRequest();
        publishCourseQuestionRequest.setCourseId(1l);
        publishCourseQuestionRequest.setDescription("A very long description for test");
        publishCourseQuestionRequest.setLectureId(2l);
        publishCourseQuestionRequest.setTitle("A Question");

        courseQuestionService.publishCourseQuestion(publishCourseQuestionRequest,"12222-33333-333","Okeme Christian");


        Page<CourseQuestion> qandAQuestionPage = courseQuestionService.findAllByLectureId(2l, PageRequest.of(0,10
                , Sort.by("createdAt")));

        assertTrue(qandAQuestionPage.hasContent());


    }


}
