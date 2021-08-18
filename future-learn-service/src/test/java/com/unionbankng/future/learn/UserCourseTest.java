package com.unionbankng.future.learn;

import com.unionbankng.future.learn.entities.*;
import com.unionbankng.future.learn.pojo.CourseEnrollmentRequest;
import com.unionbankng.future.learn.pojo.CreateCourseRequest;
import com.unionbankng.future.learn.services.CourseService;
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
                .courseTitle("Test course").description("A very long text").estimatedTimeToComplete("60 hours")
                .isPaid(false).isPublished(false).requirements("Preferably html").shortDesc("short desc").price(BigDecimal.TEN)
                .outcomes("Outcomes text").instructors(instructors).build();

        Course course = courseService.createCourse(createCourseRequest,"123456");


        CourseEnrollmentRequest courseEnrollmentRequest = new CourseEnrollmentRequest();
        courseEnrollmentRequest.setCourseEnrollingForId(course.getId());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userCourseService.enrollForFreeCourse(courseEnrollmentRequest,"122233389997");
        });

        assertEquals(400,exception.getStatus().value());



    }

    @Test
    public void enrollForCourseNotFoundTest() {

        CourseEnrollmentRequest courseEnrollmentRequest = new CourseEnrollmentRequest();
        courseEnrollmentRequest.setCourseEnrollingForId(100l);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userCourseService.enrollForFreeCourse(courseEnrollmentRequest,"122233389997");
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
                .courseTitle("Test course").description("A very long text").estimatedTimeToComplete("60 hours")
                .isPaid(false).isPublished(true).requirements("Preferably html").shortDesc("short desc").price(BigDecimal.TEN)
                .outcomes("Outcomes text").instructors(instructors).build();

        Course course = courseService.createCourse(createCourseRequest,"123456");


        CourseEnrollmentRequest courseEnrollmentRequest = new CourseEnrollmentRequest();
        courseEnrollmentRequest.setCourseEnrollingForId(course.getId());
        UserCourse userCourse = userCourseService.enrollForFreeCourse(courseEnrollmentRequest,"122233389997");


        assertEquals("122233389997",userCourse.getUserUUID());

    }


    @Test
    public void getMyCoursesTest() {

        ArrayList<Instructor> instructors = new ArrayList<>();
        Instructor instructor = new Instructor();
        instructor.setInstructorUUID("123456");
        instructors.add(instructor);

        CreateCourseRequest createCourseRequest = CreateCourseRequest.builder()
                .courseTitle("Test course").description("A very long text").estimatedTimeToComplete("60 hours")
                .isPaid(false).isPublished(true).requirements("Preferably html").shortDesc("short desc").price(BigDecimal.TEN)
                .outcomes("Outcomes text").instructors(instructors).build();

        Course course = courseService.createCourse(createCourseRequest,"123456");


        CourseEnrollmentRequest courseEnrollmentRequest = new CourseEnrollmentRequest();
        courseEnrollmentRequest.setCourseEnrollingForId(course.getId());

        userCourseService.enrollForFreeCourse(courseEnrollmentRequest,"122233389997-883u33");

        List<Course> courses = userCourseService.getMyCourses("122233389997-883u33");

        assertEquals(1,courses.size());

    }


}
