package com.unionbankng.future.futurejobservice.entities;

import com.unionbankng.future.futurejobservice.enums.JobStatus;
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
    JobStatus status;
    @Temporal(TemporalType.DATE)
    Date createdAt;
    @Temporal(TemporalType.DATE)
    Date lastModifiedDate;

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }

    @PreUpdate
    private void setLastModifiedDate() {
        createdAt = new Date();
    }

}

