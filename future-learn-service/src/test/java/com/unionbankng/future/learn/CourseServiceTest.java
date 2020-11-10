package com.unionbankng.future.learn;

import com.unionbankng.future.learn.entities.Course;
import com.unionbankng.future.learn.entities.Instructor;
import com.unionbankng.future.learn.pojo.CreateCourseRequest;
import com.unionbankng.future.learn.services.CourseService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class CourseServiceTest extends AbstractTest{

    @Autowired
    CourseService courseService;

    @Test
    public void createCourseTest()  {

        ArrayList<Instructor> instructors = new ArrayList<>();
        Instructor instructor = new Instructor();
        instructor.setInstructorUUID("123456");
        instructors.add(instructor);

        CreateCourseRequest createCourseRequest = CreateCourseRequest.builder()
                .courseTitle("Test course").description("A very long text").estimatedTimeToComplete("60 hours")
                .isPaid(false).isPublished(false).requirements("Preferably html").shortDesc("short desc").price(BigDecimal.TEN)
                .outcomes("Outcomes text").instructors(instructors).build();

        Course course = courseService.createCourse(createCourseRequest,"123456");

        Assert.assertEquals(1,course.getInstructors().size());
        Assert.assertEquals(BigDecimal.TEN,course.getPrice());

    }

    @Test
    public void findByInstructorTest()  {

        ArrayList<Instructor> instructors = new ArrayList<>();
        Instructor instructor = new Instructor();
        instructor.setInstructorUUID("1234564");
        instructors.add(instructor);

        CreateCourseRequest createCourseRequest = CreateCourseRequest.builder()
                .courseTitle("Test course").description("A very long text").estimatedTimeToComplete("60 hours")
                .isPaid(false).isPublished(false).requirements("Preferably html").shortDesc("short desc").price(BigDecimal.TEN)
                .outcomes("Outcomes text").instructors(instructors).build();

        courseService.createCourse(createCourseRequest,"123456");

        Page<Course> coursePage = courseService.findAllByInstructorsIn("1234564", PageRequest.of(0,10));

        Assert.assertEquals(1,coursePage.getNumberOfElements());

    }
}
