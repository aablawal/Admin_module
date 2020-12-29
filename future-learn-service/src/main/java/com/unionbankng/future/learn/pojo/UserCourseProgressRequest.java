package com.unionbankng.future.learn.pojo;

import com.unionbankng.future.learn.entities.Lecture;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
public class UserCourseProgressRequest {

    @NotNull
    private Long progressId;
    @NotNull
    private Long courseId;
    private String userUUID;
    @NotNull
    private Lecture lecturesTaken;
    @NotNull
    private Integer currentLectureIndex;

}
