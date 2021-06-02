package com.unionbankng.future.futurejobservice.entities;

import com.unionbankng.future.futurejobservice.enums.JobStatus;
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
    private Long id;
    @NotNull
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long employerId;
    private  Long contractId;
    @NotNull
    @Column(nullable = false)
    private Long jobId;
    @Column(columnDefinition="TEXT")
    private String  about;
    private String fullName;
    private String img;
    private String email;
    @Column(columnDefinition="TEXT")
    private String paymentTerms;
    private String preparedCurrency;
    private String supportingFiles;
    private Long bidAmount;
    private Long  duration;
    private Long percentage;
    @Column(length = 50)
    private String durationType;
    @Column(columnDefinition="TEXT")
    private String previousWorkLinks;
    @Column(columnDefinition="TEXT")
    private String socialMediaLinks;
    private String workMethod;
    @Column(columnDefinition="TEXT")
    private String milestones;
    @Column(columnDefinition="TEXT")
    private String comment;
    private String accountNumber;
    private String accountName;
    private String accountType;
    private Boolean isApplied;
    private String branchCode;
    @Column(length=3, nullable=false)
    @Enumerated(EnumType.STRING)
    private JobStatus status;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Temporal(TemporalType.DATE)
    private Date lastModifiedDate;
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @PrePersist
    public void setCreatedAt() {
        createdAt = new Date();
    }

    @PreUpdate
    public void lastModifiedDate() {
        lastModifiedDate = new Date();
    }

    @Override
    public boolean equals(Object application) {
        return this.id.equals(((JobProposal)application).getId());
    }

}
