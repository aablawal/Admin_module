package com.unionbankng.future.futurejobservice.pojos;

import com.unionbankng.future.futurejobservice.enums.DeliveryType;
import com.unionbankng.future.futurejobservice.enums.JobType;
import com.unionbankng.future.futurejobservice.enums.Status;
import lombok.Data;

import java.util.Date;

@Data
public class JobDTO {
    private Long id;
    private Long oid;
    private String title;
    private String goal;
    private String description;
    private String supportingFiles;
    private JobType type;
    private String categories;
    private String invitationId;
    private String duration;
    private Long budget;
    private String team;
    private String qualification;
    private String skillsRequired;
    private String assessment;
    private String sampleProduct;
    private boolean interview;
    private String timeline;
    private String paymentTerms;
    private String teamExpertiseLevel;
    private Long teamSize;
    private String ndaFiles;
    private Status status;
    private Date publishDate;
    private Date lastModifiedDate;
    private Boolean terms;
    private Date createdAt;
    private String category;
    private String subCategory;
    private Status job_proposal_status;
    private Date startDate;
    private Long  job_proposal_duration;
    private DeliveryType deliveryType;
    private Date job_proposal_start_data;
}
