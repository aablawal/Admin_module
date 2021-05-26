package com.unionbankng.future.futurejobservice.entities;
import com.unionbankng.future.futurejobservice.enums.JobStatus;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Table(name="job_milestones")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobMilestone {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;
    @NotNull
    Long proposalId;
    @NotNull
    Long userId;
    @NotNull
    Long employerId;
    @NotNull
    Long contractId;
    @NotNull
    Long jobId;
    @NotNull
    Long amount;
    @Temporal(TemporalType.DATE)
    Date startDate;
    @NotNull
    @Temporal(TemporalType.DATE)
    Date endDate;
    @NotNull
    @Column(columnDefinition="TEXT")
    String title;
    public String supportingFiles;
    @NotNull
    @Column(columnDefinition="TEXT")
    @NotNull
    String milestoneReference;
    String contractReference;
    String description;
    String initialPaymentReferenceA;
    String settlementPaymentReferenceA;
    String reversalPaymentReferenceA;
    String initialPaymentReferenceB;
    String settlementPaymentReferenceB;
    String reversalPaymentReferenceB;
    @NotNull
    @Enumerated(EnumType.STRING)
    JobStatus status;
    @Temporal(TemporalType.DATE)
    Date createdAt;


    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }

}

