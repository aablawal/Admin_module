package com.unionbankng.future.futurejobservice.entities;

import com.unionbankng.future.futurejobservice.enums.JobStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name="job_contract_submission")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobProjectSubmission {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;
    @NotNull
    Long proposalId;
    @NotNull
    Long userId;
    @NotNull
    Long employerId;
    @NotNull
    Long jobId;
    String link;
    @NotNull
    String contractReference;
    public String supportingFiles;
    @NotNull
    @Enumerated(EnumType.STRING)
    JobStatus status;
    @Column(columnDefinition="TEXT")
    String description;
    @Temporal(TemporalType.DATE)
    Date createdAt;

    public JobProjectSubmission(JobProjectSubmission request) {
        this.id = request.id;
        this.proposalId = request.proposalId;
        this.userId = request.userId;
        this.employerId = request.employerId;
        this.jobId = request.jobId;
        this.link = request.link;
        this.description = request.description;
        this.supportingFiles=request.supportingFiles;
        this.status=request.status;
        this.createdAt = new Date();
    }

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }

}
