package com.unionbankng.future.learn.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class AddCourseQuestionCommentRequest {

    @NotNull
    private String reply;
    @NotNull
    private Long courseQuestionId;
}
