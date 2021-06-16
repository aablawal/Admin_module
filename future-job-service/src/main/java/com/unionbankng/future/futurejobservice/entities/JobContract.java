package com.unionbankng.future.futurejobservice.entities;
import com.unionbankng.future.futurejobservice.enums.Status;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Table(name="job_contracts")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobContract implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private  Long Id;
    @NotNull
    private Long proposalId;
    @NotNull
    private  Long jobId;
    private int appId;
    @NotNull
    private String contractReference;
    @NotNull
    private String userEmail;
    @NotNull
    private double amount;
    @NotNull
    private double clearedAmount;
    @NotNull
    private String country;
    private String workMethod;
    @NotNull
    private String currency;
    private String freelancerEmailAddress;
    private String employerEmailAddress;
    private Boolean isSettled;
    private  String settlement;
    @Enumerated(EnumType.STRING)
    private Status status;
    private  Long rate;
    private  String description;
    @Column(columnDefinition="TEXT")
    private String feedback;
    @NotNull
    private String employerAccountName;
    @NotNull
    private String employerAccountNumber;
    @NotNull
    private String freelancerAccountName;
    @NotNull
    private String freelancerAccountNumber;
    private String freelancerPhoneNumber;
    private String employerPhoneNumber;
    @NotNull
    private int peppfees;
    private String initialPaymentReferenceA;
    private String settlementPaymentReferenceA;
    private String reversalPaymentReferenceA;
    private String initialPaymentReferenceB;
    private String settlementPaymentReferenceB;
    private  String reversalPaymentReferenceB;
    @Temporal(TemporalType.DATE)
    private  Date startDate;
    @Temporal(TemporalType.DATE)
    private  Date endDate;
    @Temporal(TemporalType.DATE)
    private  Date createdAt;
    @Temporal(TemporalType.DATE)
    private   Date lastModifiedDate;

    @PrePersist
    public void setCreatedAt() {
        createdAt = new Date();
    }

    @PreUpdate
    public void lastModifiedDate() {
        lastModifiedDate = new Date();
    }
}
