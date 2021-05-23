package com.unionbankng.future.futureloanservice.entities;
import com.unionbankng.future.futureloanservice.enums.JobProposalStatus;
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
    String referenceId;
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
    String customerEmail;
    String merchantEmail;
    Boolean isSettled;
    String settlement;
    JobProposalStatus status;
    Long rate;
    String description;
    @Column(columnDefinition="TEXT")
    String feedback;
    @NotNull
    String merchantAccountNumber;
    @NotNull
    String customerAccountNumber;
    @NotNull
    String merchantBankCode;
    @NotNull
    String customerBankCode;
    @NotNull
    String customerName;
    @NotNull
    String merchantName;
    String customerPhone;
    String merchantPhone;
    @NotNull
    int peppfees;
    String transferReferenceId;
    @Temporal(TemporalType.DATE)
    Date startDate;
    @Temporal(TemporalType.DATE)
    Date endDate;
    @Temporal(TemporalType.DATE)
    Date createdAt;
    @Temporal(TemporalType.DATE)
    Date lastModifiedDate;

    public JobContract(JobContract request) {
        this.proposalId= request.proposalId;
        this.jobId=request.jobId;
        this.appId = request.appId;
        this.referenceId =  request.referenceId;
        this.userEmail =  request.userEmail;
        this.amount =  request.amount;
        this.isSettled=request.isSettled;
        this.settlement=request.settlement;
        this.clearedAmount=request.clearedAmount;
        this.status=request.status;
        this.country =  request.country;
        this.currency =  request.currency;
        this.customerEmail =  request.customerEmail;
        this.merchantEmail =  request.merchantEmail;
        this.rate =request.rate;
        this.description=request.description;
        this.feedback=request.feedback;
        this.merchantAccountNumber =  request.merchantAccountNumber;
        this.customerAccountNumber=request.customerAccountNumber;
        this.merchantBankCode =  request.merchantBankCode;
        this.customerBankCode=request.customerBankCode;
        this.customerName =  request.customerName;
        this.merchantName =  request.merchantName;
        this.customerPhone =  request.customerPhone;
        this.merchantPhone =  request.merchantPhone;
        this.peppfees =  request.peppfees;
        this.startDate =  request.startDate;
        this.endDate =  request.endDate;
        this.transferReferenceId =  request.transferReferenceId;
        this.createdAt=new Date();
    }

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }

    @PreUpdate
    private void lastModifiedDate() {
        lastModifiedDate = new Date();
    }
}