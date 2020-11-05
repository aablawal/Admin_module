package com.unionbankng.future.learn.pojo;

import com.unionbankng.future.learn.entities.Question;
import com.unionbankng.future.learn.enums.LectureType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateLectureRequest {

    @NotNull
    private String title;
    @NotNull
    private LectureType type;
    private String duration;
    @NotNull
    private Integer index;
    private List<Question> questionList = new ArrayList<>();
    private Integer answerIndex;
    private String creatorUUID;
    private Long courseContentId;
    private Long courseId;
}
