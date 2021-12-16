package com.unionbankng.future.authorizationserver.pojos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TrainingRequest {

    private Long trainingId;

    @NotNull
    private Long profileId;

    @NotNull
    private String title;

    private String organization;

    @NotNull//2012
    private String yearAwarded;

    @NotNull//2012
    private String linkOrId;

    private String description;

}
