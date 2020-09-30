package com.unionbankng.future.authorizationserver.pojos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserSkillRequest {
    @NotNull
    private Long profileId;
    @NotNull
    private Long skillId;
}
