package com.unionbankng.future.learn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.futureutilityservice.grpcserver.StreamingLocatorResponse;
import com.unionbankng.future.learn.entities.CourseContent;
import com.unionbankng.future.learn.entities.Lecture;
import com.unionbankng.future.learn.entities.Question;
import com.unionbankng.future.learn.entities.QuestionOption;
import com.unionbankng.future.learn.enums.LectureType;
import com.unionbankng.future.learn.pojo.CourseContentRequest;
import com.unionbankng.future.learn.pojo.CreateLectureRequest;
import com.unionbankng.future.learn.services.CourseContentService;
import com.unionbankng.future.learn.services.FutureStreamingService;
import com.unionbankng.future.learn.services.LectureService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LectureServiceTest extends AbstractTest{

    @Autowired
    LectureService lectureService;

    @MockBean
    FutureStreamingService futureStreamingService;


    @Test
    public void createNewVideoLectureTest() throws IOException, InterruptedException {

        CreateLectureRequest request = new CreateLectureRequest();
        request.setCourseContentId(1l);
        request.setCourseId(1l);
        request.setIndex(1);
        request.setDuration("30:00");
        request.setType(LectureType.VIDEO);
        request.setTitle("Test");

        MockMultipartFile firstFile = new MockMultipartFile("file", "filename.mp4", "video/mp4", "Hello world".getBytes());

        StreamingLocatorResponse streamingLocatorResponse = StreamingLocatorResponse.newBuilder()
                .setLocatorName("testLocator").setAssetName("assetName").setSuccess(true).build();
        Mockito.when(futureStreamingService.uploadAndGetStreamingLocator(firstFile)).thenReturn(streamingLocatorResponse);


        Lecture lecture = lectureService.createNewLecture(firstFile,request,"1233344455555-87666665-ui8886677666");

        Assert.assertEquals("testLocator",lecture.getStreamingLocatorName());
        Assert.assertEquals("assetName",lecture.getOutputAssetName());

    }

    @Test
    public void createNewQuizLectureNoQuestionsTest() {

        CreateLectureRequest request = new CreateLectureRequest();
        request.setCourseContentId(1l);
        request.setCourseId(1l);
        request.setIndex(2);
        request.setDuration("30:00");
        request.setType(LectureType.QUIZ);
        request.setTitle("Test");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            lectureService.createNewLecture(null,request,"1233344455555-87666665-ui8886677666");
        });

        assertEquals(400,exception.getStatus().value());


    }

    @Test
    public void createNewQuizLectureQuestionWithoutOptionsTest() {

        List<Question> questionList = new ArrayList<>();
        Question q = new Question();
        q.setLectureId(1l);
        q.setAnswerIndex(0);
        q.setIndexNo(0);
        q.setQuestionText("What is your name ?");
        questionList.add(q);


        CreateLectureRequest request = new CreateLectureRequest();
        request.setCourseContentId(1l);
        request.setQuestionList(questionList);
        request.setCourseId(1l);
        request.setIndex(3);
        request.setType(LectureType.QUIZ);
        request.setTitle("Test");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            lectureService.createNewLecture(null,request,"1233344455555-87666665-ui8886677666");
        });

        assertEquals(400,exception.getStatus().value());


    }

    @Test
    public void createNewQuizLectureSuccessTest() throws IOException {

        List<Question> questionList = new ArrayList<>();
        Question q = new Question();
        q.setLectureId(1l);
        q.setIndexNo(1);
        q.setAnswerIndex(0);
        q.setQuestionText("What is your name ?");

        for(int i = 0; i < 3; i++){
            QuestionOption questionOption = new QuestionOption();
            questionOption.setIndexNo(i);
            questionOption.setOptionText("sidekiq"+ (i == 0 ? "":Integer.toString(i)));
            q.getOptions().add(questionOption);
        }
        questionList.add(q);


        CreateLectureRequest request = new CreateLectureRequest();
        request.setCourseContentId(1l);
        request.setQuestionList(questionList);
        request.setCourseId(1l);
        request.setIndex(2);
        request.setType(LectureType.QUIZ);
        request.setTitle("Test");

         Lecture lecture =  lectureService.createNewLecture(null,request,"1233344455555-87666665-ui8886677666");


        assertEquals(1,lecture.getQuestions().size());
        assertEquals(3,lecture.getQuestions().get(0).getOptions().size());


    }

}
