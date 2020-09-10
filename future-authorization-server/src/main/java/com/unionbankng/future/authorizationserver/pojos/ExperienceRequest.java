package com.unionbankng.future.authorizationserver.pojos;

import com.unionbankng.future.authorizationserver.enums.EmploymentType;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class ExperienceRequest {
    private Long experienceId;
    @NotNull
    private Long userId;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private EmploymentType employmentType;
    @NotNull
    private String company;
    @NotNull
    private Boolean current;
    @NotNull//12/2012
    private String startDate;
    @NotNull//12/2012
    private String endDate;
    private String headline;
    private String media;
}
