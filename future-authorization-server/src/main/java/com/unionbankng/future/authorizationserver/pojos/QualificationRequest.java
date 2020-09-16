package com.unionbankng.future.authorizationserver.pojos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class QualificationRequest {
    private Long qualificationId;
    @NotNull
    private Long userId;
    @NotNull
    private String school;
    @NotNull
    private String degree;
    private String fieldOfStudy;
    @NotNull//2012
    private String startYear;
    @NotNull//2012
    private String endYear;
    private String grade;
    private String description;
    private String activities;
    private String media;
}
