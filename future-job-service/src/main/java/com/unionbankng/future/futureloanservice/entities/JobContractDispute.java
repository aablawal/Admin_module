package com.unionbankng.future.futureloanservice.entities;

import com.unionbankng.future.futureloanservice.enums.JobContractDisputeStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Table(name="job_contract_disputes")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobContractDispute {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;
    @NotNull
    Long proposalId;
    @NotNull
    Long contractId;
    @NotNull
    Long userId;
    @NotNull
    Long employerId;
    @NotNull
    Long jobId;
    @Column(columnDefinition="TEXT")
    String description;
    @Column(columnDefinition="TEXT")
    String referenceId;
    public String attachment;
    @NotNull
    @Enumerated(EnumType.STRING)
    JobContractDisputeStatus status;
    @Temporal(TemporalType.DATE)
    Date createdAt;
    @Temporal(TemporalType.DATE)
    Date lastModifiedDate;

    public JobContractDispute(JobContractDispute request) {
        this.id = request.id;
        this.proposalId = request.proposalId;
        this.userId = request.userId;
        this.employerId = request.employerId;
        this.jobId = request.jobId;
        this.referenceId=request.referenceId;
        this.description=request.description;
        this.attachment=request.attachment;
        this.status=request.status;
        this.createdAt = new Date();
        this.lastModifiedDate=new Date();
    }

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }

    @PreUpdate
    private void setLastModifiedDate() {
        createdAt = new Date();
    }

}

