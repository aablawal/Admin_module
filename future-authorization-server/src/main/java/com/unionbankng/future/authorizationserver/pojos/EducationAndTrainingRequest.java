package com.unionbankng.future.authorizationserver.pojos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EducationAndTrainingRequest {
    private Long qualificationId;
    @NotNull
    private Long profileId;
    @NotNull
    private String school;

    private String country;
    @NotNull
    private String degree;
    private String fieldOfStudy;
    @NotNull//2012
    private String startYear;

    private String endYear;
    private String grade;
    private String description;
    private String activities;
    private String media;


    private Long trainingId;

    @NotNull
    private String trainingTitle;

    private String trainingOrganization;

    @NotNull//2012
    private String trainingYearAwarded;

    @NotNull//2012
    private String trainingLinkOrId;

    private String trainingDescription;
}
