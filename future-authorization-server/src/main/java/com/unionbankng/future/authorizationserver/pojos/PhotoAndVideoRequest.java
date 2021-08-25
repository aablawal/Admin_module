package com.unionbankng.future.authorizationserver.pojos;

import com.unionbankng.future.authorizationserver.enums.EmploymentType;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
public class PhotoAndVideoRequest {
    private Long id;
    @NotNull
    private Long profileId;
    @NotNull
    private String title;
    private String comment;
}
