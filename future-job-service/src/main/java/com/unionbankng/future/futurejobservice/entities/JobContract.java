package com.unionbankng.future.futurejobservice.entities;
import com.unionbankng.future.futurejobservice.enums.JobStatus;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name="job_contracts")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobContract {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long Id;
    @NotNull
    Long proposalId;
    @NotNull
    Long jobId;
    int appId;
    @NotNull
    String contractReference;
    @NotNull
    String userEmail;
    @NotNull
    int amount;
    @NotNull
    int clearedAmount;
    @NotNull
    String country;
    String workMethod;
    @NotNull
    String currency;
    String freelancerEmailAddress;
    String employerEmailAddress;
    Boolean isSettled;
    String settlement;
    JobStatus status;
    Long rate;
    String description;
    @Column(columnDefinition="TEXT")
    String feedback;
    @NotNull
    String employerAccountName;
    @NotNull
    String employerAccountNumber;
    @NotNull
    String freelancerAccountName;
    @NotNull
    String freelancerAccountNumber;
    String freelancerPhoneNumber;
    String employerPhoneNumber;
    @NotNull
    int peppfees;
    String initialPaymentReferenceA;
    String settlementPaymentReferenceA;
    String reversalPaymentReferenceA;
    String initialPaymentReferenceB;
    String settlementPaymentReferenceB;
    String reversalPaymentReferenceB;
    @Temporal(TemporalType.DATE)
    Date startDate;
    @Temporal(TemporalType.DATE)
    Date endDate;
    @Temporal(TemporalType.DATE)
    Date createdAt;
    @Temporal(TemporalType.DATE)
    Date lastModifiedDate;

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }

    @PreUpdate
    private void lastModifiedDate() {
        lastModifiedDate = new Date();
    }
}
