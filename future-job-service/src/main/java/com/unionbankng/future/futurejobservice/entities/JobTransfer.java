package com.unionbankng.future.futurejobservice.entities;
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

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }

}
