package com.unionbankng.future.learn;

import com.unionbankng.future.learn.entities.CourseContent;
import com.unionbankng.future.learn.pojo.CourseContentRequest;
import com.unionbankng.future.learn.services.CourseContentService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CourseContentServiceTest extends AbstractTest{

    @Autowired
    CourseContentService courseContentService;

    @Test
    public void courseContentCreateTest()  {

        CourseContentRequest courseContentRequest = new CourseContentRequest();
        courseContentRequest.setCourseContentText("Section 1");
        courseContentRequest.setCourseId(1l);
        courseContentRequest.setIndex(1);

        CourseContent courseContent = courseContentService.createNewContent(courseContentRequest,"12333333333");

        Assert.assertEquals(Integer.valueOf(1),courseContent.getIndexNo());

    }

}
