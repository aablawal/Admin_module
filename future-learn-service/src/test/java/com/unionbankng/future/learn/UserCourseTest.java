package com.unionbankng.future.learn;

import com.unionbankng.future.learn.entities.*;
import com.unionbankng.future.learn.pojo.CourseEnrollmentRequest;
import com.unionbankng.future.learn.pojo.CreateCourseRequest;
import com.unionbankng.future.learn.services.CourseService;
import com.unionbankng.future.learn.services.QuestionOptionService;
import com.unionbankng.future.learn.services.QuestionService;
import com.unionbankng.future.learn.services.UserCourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserCourseTest extends AbstractTest{

    @Autowired
    UserCourseService userCourseService;
    @Autowired
    CourseService courseService;



    @Test
    public void enrollForCourseNotPublishedTest() {

        ArrayList<Instructor> instructors = new ArrayList<>();
        Instructor instructor = new Instructor();
        instructor.setInstructorUUID("123456");
        instructors.add(instructor);

        CreateCourseRequest createCourseRequest = CreateCourseRequest.builder()
                .courseTitle("Test course").creatorUUID("123456").description("A very long text").estimatedTimeToComplete("60 hours")
                .isPaid(false).isPublished(false).requirements("Preferably html").shortDesc("short desc").price(BigDecimal.TEN)
                .outcomes("Outcomes text").instructors(instructors).build();

        Course course = courseService.createCourse(createCourseRequest);


        CourseEnrollmentRequest courseEnrollmentRequest = new CourseEnrollmentRequest();
        courseEnrollmentRequest.setCourseEnrollingForId(course.getId());
        courseEnrollmentRequest.setUserUUID("122233389997");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userCourseService.enrollForCourse(courseEnrollmentRequest);
        });

        assertEquals(400,exception.getStatus().value());



    }

    @Test
    public void enrollForCourseNotFoundTest() {

        CourseEnrollmentRequest courseEnrollmentRequest = new CourseEnrollmentRequest();
        courseEnrollmentRequest.setCourseEnrollingForId(3l);
        courseEnrollmentRequest.setUserUUID("122233389997");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userCourseService.enrollForCourse(courseEnrollmentRequest);
        });

        assertEquals(404,exception.getStatus().value());


    }

    @Test
    public void enrollForCourseSuccessfulTest() {

        ArrayList<Instructor> instructors = new ArrayList<>();
        Instructor instructor = new Instructor();
        instructor.setInstructorUUID("123456");
        instructors.add(instructor);

        CreateCourseRequest createCourseRequest = CreateCourseRequest.builder()
                .courseTitle("Test course").creatorUUID("123456").description("A very long text").estimatedTimeToComplete("60 hours")
                .isPaid(false).isPublished(true).requirements("Preferably html").shortDesc("short desc").price(BigDecimal.TEN)
                .outcomes("Outcomes text").instructors(instructors).build();

        Course course = courseService.createCourse(createCourseRequest);


        CourseEnrollmentRequest courseEnrollmentRequest = new CourseEnrollmentRequest();
        courseEnrollmentRequest.setCourseEnrollingForId(course.getId());
        courseEnrollmentRequest.setUserUUID("122233389997");

        UserCourse userCourse = userCourseService.enrollForCourse(courseEnrollmentRequest);


        assertEquals("122233389997",userCourse.getUserUUID());

    }


    @Test
    public void getMyCoursesTest() {

        ArrayList<Instructor> instructors = new ArrayList<>();
        Instructor instructor = new Instructor();
        instructor.setInstructorUUID("123456");
        instructors.add(instructor);

        CreateCourseRequest createCourseRequest = CreateCourseRequest.builder()
                .courseTitle("Test course").creatorUUID("123456").description("A very long text").estimatedTimeToComplete("60 hours")
                .isPaid(false).isPublished(true).requirements("Preferably html").shortDesc("short desc").price(BigDecimal.TEN)
                .outcomes("Outcomes text").instructors(instructors).build();

        Course course = courseService.createCourse(createCourseRequest);


        CourseEnrollmentRequest courseEnrollmentRequest = new CourseEnrollmentRequest();
        courseEnrollmentRequest.setCourseEnrollingForId(course.getId());
        courseEnrollmentRequest.setUserUUID("122233389997-883u33");

        userCourseService.enrollForCourse(courseEnrollmentRequest);

        List<Course> courses = userCourseService.getMyCourses("122233389997-883u33");

        assertEquals(1,courses.size());

    }


}
