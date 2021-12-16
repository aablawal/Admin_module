package com.unionbankng.future.authorizationserver.pojos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProfileSkillRequest {

    private Long profileId;

//    @NotNull
    private List<String> skills;
}
