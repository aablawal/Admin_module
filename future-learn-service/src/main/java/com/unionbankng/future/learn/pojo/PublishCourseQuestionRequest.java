package com.unionbankng.future.learn.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class PublishCourseQuestionRequest {

    @Size(min = 6, max = 255)
    private String title;
    @NotNull
    private String description;
    @NotNull
    private Long courseId;
    @NotNull
    private Long lectureId;
}
