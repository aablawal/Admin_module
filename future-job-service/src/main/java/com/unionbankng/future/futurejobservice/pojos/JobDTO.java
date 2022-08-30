package com.unionbankng.future.futurejobservice.pojos;

import com.unionbankng.future.futurejobservice.enums.DeliveryType;
import com.unionbankng.future.futurejobservice.enums.JobType;
import com.unionbankng.future.futurejobservice.enums.Status;
import lombok.Data;

import java.util.Date;

@Data
public class JobDTO {
    private Long id;
    private String assessment;
    private Long budget;
    private String categories;
    private String category;
    private Date createdAt;
    private DeliveryType deliveryType;
    private String description;
    private String duration;
    private String goal;
    private Long oid;
    private Date publishDate;
    private String title;
    private String qualification;
    private String skillsRequired;
    private Status status;
    private JobType type;
    private String timeline;
    private String subCategory;
    private Date startDate;
}

