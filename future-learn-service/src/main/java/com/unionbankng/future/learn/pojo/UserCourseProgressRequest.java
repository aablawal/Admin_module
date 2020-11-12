package com.unionbankng.future.learn.pojo;

import com.unionbankng.future.learn.entities.Lecture;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
public class UserCourseProgressRequest {

    private Long progressId;
    private Long courseId;
    private String userUUID;
    private Lecture lecturesTaken;
    private Integer currentLectureId;

}
