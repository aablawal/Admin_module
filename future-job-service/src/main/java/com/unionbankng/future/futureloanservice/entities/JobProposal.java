package com.unionbankng.future.futureloanservice.entities;

import com.unionbankng.future.futureloanservice.enums.JobProposalStatus;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Table(name="job_proposals")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobProposal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @NotNull
    @Column(nullable = false)
    public Long userId;
    @Column(nullable = false)
    public Long employerId;
    Long contractId;
    @NotNull
    @Column(nullable = false)
    public Long jobId;
    @Column(columnDefinition="TEXT")
    public String  about;
    public String fullName;
    public String img;
    public String email;
    @Column(columnDefinition="TEXT")
    public String paymentTerms;
    public String preparedCurrency;
    public String supportingFiles;
    public Long bidAmount;
    public Long  duration;
    public Long percentage;
    @Column(length = 50)
    public String durationType;
    @Column(columnDefinition="TEXT")
    public String previousWorkLinks;
    @Column(columnDefinition="TEXT")
    public String socialMediaLinks;
    public String workMethod;
    @Column(columnDefinition="TEXT")
    public String milestones;
    @Column(columnDefinition="TEXT")
    public String comment;
    public String accountNumber;
    public String accountName;
    public String accountType;
    public Boolean isApplied;
    public String branchCode;
    @Column(length=3, nullable=false)
    @Enumerated(EnumType.STRING)
    public JobProposalStatus status;
    @Temporal(TemporalType.DATE)
    public Date startDate;
    @Temporal(TemporalType.DATE)
    public Date endDate;
    @Temporal(TemporalType.DATE)
    public Date lastModifiedDate;
    @Temporal(TemporalType.DATE)
    public Date createdAt;

    public JobProposal(JobProposal application) {
        this.id =application. id;
        this.userId =application.userId;
        this.jobId =application.jobId;
        this.employerId =application.employerId;
        this.contractId=application.contractId;
        this.about =application.about;
        this.fullName=application.fullName;
        this.email=application.email;
        this.img=application.img;
        this.isApplied=application.isApplied;
        this.percentage=application.percentage;
        this.paymentTerms =application.paymentTerms;
        this.preparedCurrency =application.preparedCurrency;
        this.supportingFiles =application.supportingFiles;
        this.bidAmount =application.bidAmount;
        this.previousWorkLinks =application.previousWorkLinks;
        this.socialMediaLinks =application.socialMediaLinks;
        this.workMethod =application.workMethod;
        this.milestones =application.milestones;
        this.comment =application.comment;
        this.accountNumber =application.accountNumber;
        this.accountNumber =application.accountNumber;
        this.branchCode=application.branchCode;
        this.accountType=application.accountType;
        this.status =application.status;
        this.startDate =application.startDate;
        this.endDate =application.endDate;
        this.lastModifiedDate =new Date();
        this.createdAt =new Date();
    }

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }

    @PreUpdate
    private void lastModifiedDate() {
        lastModifiedDate = new Date();
    }

    @Override
    public boolean equals(Object application) {
        return this.id.equals(((JobProposal)application).getId());
    }

}