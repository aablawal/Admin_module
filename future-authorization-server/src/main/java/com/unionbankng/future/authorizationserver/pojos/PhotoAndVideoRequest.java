package com.unionbankng.future.authorizationserver.pojos;

import com.unionbankng.future.authorizationserver.enums.EmploymentType;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
public class PhotoAndVideoRequest {
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private String title;
    private String comment;
}
