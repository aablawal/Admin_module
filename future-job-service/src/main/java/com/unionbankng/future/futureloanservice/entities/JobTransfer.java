package com.unionbankng.future.futureloanservice.entities;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name="job_transfers")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @NotNull
    Long proposalId;
    @NotNull
    Long userId;
    @NotNull
    Long employerId;
    @NotNull
    Long jobId;
    @NotNull
    int amount;
    @NotNull
    String creditAccountName;
    @NotNull
    String creditAccountBankCode;
    @NotNull
    String creditAccountNumber;
    @NotNull
    String creditNarration;
    @NotNull
    String creditAccountBranchCode;
    @NotNull
    String debitAccountBranchCode;
    String creditAccountType;
    String currency;
    @NotNull
    String debitAccountName;
    @NotNull
    String debitAccountNumber;
    String debitNarration;
    String debitAccountType;
    String initBranchCode;
    String paymentReference;
    @Temporal(TemporalType.DATE)
    Date createdAt;

    public JobTransfer(JobTransfer transfer) {
        this.id = transfer.id;
        this.proposalId = transfer.proposalId;
        this.userId =  transfer.userId;
        this.employerId =  transfer.employerId;
        this.jobId =  transfer.jobId;
        this.amount =  transfer.amount;
        this.creditAccountName =  transfer.creditAccountName;
        this.creditAccountBankCode =  transfer.creditAccountBankCode;
        this.creditAccountNumber =  transfer.creditAccountNumber;
        this.creditNarration =  transfer.creditNarration;
        this.creditAccountBranchCode =  transfer.creditAccountBranchCode;
        this.debitAccountBranchCode =  transfer.debitAccountBranchCode;
        this.creditAccountType =  transfer.creditAccountType;
        this.currency =  transfer.currency;
        this.debitAccountName =  transfer.debitAccountName;
        this.debitAccountNumber =  transfer.debitAccountNumber;
        this.debitNarration =  transfer.debitNarration;
        this.debitAccountType =  transfer.debitAccountType;
        this.initBranchCode =  transfer.initBranchCode;
        this.paymentReference =  transfer.paymentReference;
        this.createdAt =  new Date();
    }

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }

}
