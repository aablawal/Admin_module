package com.unionbankng.future.learn.pojo;

import com.unionbankng.future.learn.entities.Instructor;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CreateCourseRequest {

    private String description;
    private String requirements;
    private Boolean isPaid;
    private BigDecimal price;
    private String courseTitle;
    private String shortDesc;
    private String outcomes;
    private Boolean isPublished;
    private List<Instructor> instructors;
    private String estimatedTimeToComplete;
    private String creatorUUID;
}
