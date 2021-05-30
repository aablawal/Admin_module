package com.unionbankng.future.futurejobservice.entities;
import lombok.*;
import org.apache.commons.lang3.SerializationUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name="job_bulk_transfers")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobBulkPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String executedBy;
	String executedFor;
	@NotNull
	String initialPaymentReference;
	String paymentReference;
	String transactionId;
	@NotNull
	String accountNumber;
	@NotNull
	String accountType;
	@NotNull
	String accountName;
	@NotNull
	String accountBranchCode;
	@NotNull
	String accountBankCode;
	String narration;
	String instrumentNumber;
	@NotNull
	int amount;
	String valueDate;
	String crDrFlag;
	String feeOrCharges;
	@Temporal(TemporalType.DATE)
	Date createdAt;

	@PrePersist
	private void setCreatedAt() {
		createdAt = new Date();
	}
}