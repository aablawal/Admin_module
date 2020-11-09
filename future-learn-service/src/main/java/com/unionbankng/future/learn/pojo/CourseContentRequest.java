package com.unionbankng.future.learn.pojo;

import lombok.Data;


@Data
public class CourseContentRequest {
    private Integer index;
    private String courseContentText;
    private Long courseId;

}
