package com.unionbankng.future.authorizationserver.pojos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EntitySkillRequest {
    @NotNull
    private Long entityId;
    @NotNull
    private Long skillId;
}
