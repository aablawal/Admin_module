package com.unionbankng.future.authorizationserver.pojos;

import com.unionbankng.future.authorizationserver.enums.EmploymentType;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class ExperienceRequest {

    private Long experienceId;

    private Long userId;

    private Long profileId;

    @NotNull
    private String title;


    private String description;


    private EmploymentType employmentType;

    @NotNull
    private String company;

    @NotNull
    private Boolean current;

    @NotNull
    private String startDate;

    private String endDate;

    private String headline;

    private String media;
}
