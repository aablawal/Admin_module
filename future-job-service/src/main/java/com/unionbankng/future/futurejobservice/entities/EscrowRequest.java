package com.unionbankng.future.futurejobservice.entities;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name="escrow_transactions")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EscrowRequest {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long Id;
    @NotNull
    int appId;
    @NotNull
    String referenceId;
    @NotNull
    String userEmail;
    @NotNull
    int amount;
    @NotNull
    String country;
    @NotNull
    String currency;
    @NotNull
    String customerEmail;
    @NotNull
    String merchantEmail;
    @NotNull
    String merchantAccountNumber;
    String merchantBankCode;
    @NotNull
    String customerName;
    @NotNull
    String merchantName;
    @NotNull
    String customerPhone;
    @NotNull
    String merchantPhone;
    @NotNull
    int peppfees;
    @Temporal(TemporalType.DATE)
    Date startDate;
    @Temporal(TemporalType.DATE)
    Date endDate;
    @Temporal(TemporalType.DATE)
    Date createdAt;
    @Temporal(TemporalType.DATE)
    Date lastModifiedDate;
    String transferReferenceId;

    public EscrowRequest(EscrowRequest request) {
        this.Id =  request.Id;
        this.appId = request.appId;
        this.referenceId =  request.referenceId;
        this.userEmail =  request.userEmail;
        this.amount =  request.amount;
        this.country =  request.country;
        this.currency =  request.currency;
        this.customerEmail =  request.customerEmail;
        this.merchantEmail =  request.merchantEmail;
        this.merchantAccountNumber =  request.merchantAccountNumber;
        this.merchantBankCode =  request.merchantBankCode;
        this.customerName =  request.customerName;
        this.merchantName =  request.merchantName;
        this.customerPhone =  request.customerPhone;
        this.merchantPhone =  request.merchantPhone;
        this.peppfees =  request.peppfees;
        this.startDate =  request.startDate;
        this.endDate =  request.endDate;
        this.transferReferenceId =  request.transferReferenceId;
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
