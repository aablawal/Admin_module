package com.unionbankng.future.learn.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateAnnouncementRequest {

    private Long id;
    @NotNull
    private String announcementText;
    @NotNull
    private Long courseId;
}
