package com.unionbankng.future.learn.pojo;

import lombok.Data;

@Data
public class CourseEnrollmentRequest {
    private String userUUID;
    private Long courseEnrollingForId;
}
