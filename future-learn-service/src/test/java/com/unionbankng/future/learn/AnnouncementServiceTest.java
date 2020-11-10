package com.unionbankng.future.learn;

import com.unionbankng.future.learn.entities.Announcement;
import com.unionbankng.future.learn.entities.Course;
import com.unionbankng.future.learn.entities.Instructor;
import com.unionbankng.future.learn.entities.LectureNote;
import com.unionbankng.future.learn.pojo.CreateAnnouncementRequest;
import com.unionbankng.future.learn.pojo.CreateCourseRequest;
import com.unionbankng.future.learn.services.AnnouncementService;
import com.unionbankng.future.learn.services.CourseService;
import com.unionbankng.future.learn.services.LectureNoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AnnouncementServiceTest extends AbstractTest{

    @Autowired
    AnnouncementService announcementService;
    @Autowired
    CourseService courseService;

    @Test
    public void createAnnouncementCourseNotFoundTest() {

        CreateAnnouncementRequest createAnnouncementRequest = new CreateAnnouncementRequest();
        createAnnouncementRequest.setCourseId(10l);
        createAnnouncementRequest.setAnnouncementText("Very long announcement text; preferably html");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            announcementService.createOrUpdateAnnouncement(createAnnouncementRequest,"123333-7888888-89999");
        });

        assertEquals(404,exception.getStatus().value());



    }

    @Test
    public void createAnnouncementCourseNotAuthorizeTest() {

        ArrayList<Instructor> instructors = new ArrayList<>();
        Instructor instructor = new Instructor();
        instructor.setInstructorUUID("1234562");
        instructors.add(instructor);

        CreateCourseRequest createCourseRequest = CreateCourseRequest.builder()
                .courseTitle("Test course").description("A very long text").estimatedTimeToComplete("60 hours")
                .isPaid(false).isPublished(false).requirements("Preferably html").shortDesc("short desc").price(BigDecimal.TEN)
                .outcomes("Outcomes text").instructors(instructors).build();

        Course course = courseService.createCourse(createCourseRequest,"123456");

        CreateAnnouncementRequest createAnnouncementRequest = new CreateAnnouncementRequest();
        createAnnouncementRequest.setCourseId(course.getId());
        createAnnouncementRequest.setAnnouncementText("Very long announcement text; preferably html");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            announcementService.createOrUpdateAnnouncement(createAnnouncementRequest,"123333-7888888-89999");
        });

        assertEquals(400,exception.getStatus().value());



    }

    @Test
    public void createAnnouncementSuccessAuthorizeTest() {

        ArrayList<Instructor> instructors = new ArrayList<>();
        Instructor instructor = new Instructor();
        instructor.setInstructorUUID("123333-7888888-89999");
        instructors.add(instructor);

        CreateCourseRequest createCourseRequest = CreateCourseRequest.builder()
                .courseTitle("Test course").description("A very long text").estimatedTimeToComplete("60 hours")
                .isPaid(false).isPublished(Boolean.TRUE).requirements("Preferably html").shortDesc("short desc").price(BigDecimal.TEN)
                .outcomes("Outcomes text").instructors(instructors).build();

        Course course = courseService.createCourse(createCourseRequest,"123456");

        CreateAnnouncementRequest createAnnouncementRequest = new CreateAnnouncementRequest();
        createAnnouncementRequest.setCourseId(course.getId());
        createAnnouncementRequest.setAnnouncementText("Very long announcement text; preferably html");

        Announcement announcement = announcementService.createOrUpdateAnnouncement(createAnnouncementRequest,"123333-7888888-89999");


        assertEquals("123333-7888888-89999",announcement.getPosterUUID());



    }



}
