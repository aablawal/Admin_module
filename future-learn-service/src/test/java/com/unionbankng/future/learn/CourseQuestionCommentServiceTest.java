package com.unionbankng.future.learn;

import com.unionbankng.future.learn.entities.CourseQuestionComment;
import com.unionbankng.future.learn.entities.CourseQuestion;
import com.unionbankng.future.learn.pojo.AddCourseQuestionCommentRequest;
import com.unionbankng.future.learn.pojo.PublishCourseQuestionRequest;
import com.unionbankng.future.learn.services.CourseQuestionCommentService;
import com.unionbankng.future.learn.services.CourseQuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

public class CourseQuestionCommentServiceTest extends AbstractTest{

    @Autowired
    CourseQuestionCommentService courseQuestionCommentService;

    @Autowired
    CourseQuestionService courseQuestionService;

    @Test
    public void addCommentToQuestionNotExist() {

        AddCourseQuestionCommentRequest addCourseQuestionCommentRequest = new AddCourseQuestionCommentRequest();
        addCourseQuestionCommentRequest.setCourseQuestionId(300l);
        addCourseQuestionCommentRequest.setReply("A very long reply for test");


        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {

            courseQuestionCommentService.addCommentToQuestion(addCourseQuestionCommentRequest,"12222-33333-333","Okeme Christian");
        });

        assertEquals(404,exception.getStatus().value());



    }

    @Test
    public void addCommentToQuestion() {

        PublishCourseQuestionRequest publishCourseQuestionRequest = new PublishCourseQuestionRequest();
        publishCourseQuestionRequest.setCourseId(1l);
        publishCourseQuestionRequest.setDescription("A very long description for test");
        publishCourseQuestionRequest.setLectureId(2l);
        publishCourseQuestionRequest.setTitle("A Question");

        CourseQuestion courseQuestion = courseQuestionService.publishCourseQuestion(publishCourseQuestionRequest,"12222-33333-333","Okeme Christian");


        AddCourseQuestionCommentRequest addCourseQuestionCommentRequest = new AddCourseQuestionCommentRequest();
        addCourseQuestionCommentRequest.setCourseQuestionId(courseQuestion.getId());
        addCourseQuestionCommentRequest.setReply("A very long reply for test");


        CourseQuestionComment courseQuestionComment = courseQuestionCommentService.addCommentToQuestion(addCourseQuestionCommentRequest,"12222-33333-333","Okeme Christian");


        assertEquals("12222-33333-333", courseQuestionComment.getCreatorUUID());




    }

    @Test
    public void findAnswersForQuestionTest() {

        PublishCourseQuestionRequest publishCourseQuestionRequest = new PublishCourseQuestionRequest();
        publishCourseQuestionRequest.setCourseId(1l);
        publishCourseQuestionRequest.setDescription("A very long description for test");
        publishCourseQuestionRequest.setLectureId(2l);
        publishCourseQuestionRequest.setTitle("A Question");

        CourseQuestion courseQuestion = courseQuestionService.publishCourseQuestion(publishCourseQuestionRequest,"12222-33333-333","Okeme Christian");


        AddCourseQuestionCommentRequest addCourseQuestionCommentRequest = new AddCourseQuestionCommentRequest();
        addCourseQuestionCommentRequest.setCourseQuestionId(courseQuestion.getId());
        addCourseQuestionCommentRequest.setReply("A very long reply for test");


        courseQuestionCommentService.addCommentToQuestion(addCourseQuestionCommentRequest,"12222-33333-333","Okeme Christian");



        Page<CourseQuestionComment> qandAComments = courseQuestionCommentService.findAllByCourseQuestionId(courseQuestion.getId(), PageRequest.of(0,10
                , Sort.by("createdAt")));

        assertTrue(qandAComments.hasContent());


    }


}
