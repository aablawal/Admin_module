package com.unionbankng.future.learn.pojo;

import com.unionbankng.future.learn.entities.Instructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CreateCourseRequest {

    @NotNull
    private String description;
    @NotNull
    private String requirements;
    @NotNull
    private Boolean isPaid;
    private BigDecimal price;
    @NotNull
    private String courseTitle;
    @NotNull
    private String shortDesc;
    private String outcomes;
    @NotNull
    private Boolean isPublished;
    private List<Instructor> instructors;
    @NotNull
    private String estimatedTimeToComplete;
    private String accountNumber;
    private String accountName;
    private String paymentTerms;
}
