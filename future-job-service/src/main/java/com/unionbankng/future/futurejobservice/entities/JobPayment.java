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
@AllArgsConstructor
public class JobPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String  executedBy;
    String  executedFor;
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
    String initialPaymentReference;
    String paymentReference;
    @Temporal(TemporalType.DATE)
    Date createdAt;

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }

}
