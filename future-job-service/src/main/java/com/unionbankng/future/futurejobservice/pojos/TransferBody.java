package com.unionbankng.future.futurejobservice.pojos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
public class TransferBody {

    int amount;
    String creditAccountName;
    String creditAccountBankCode;
    String creditAccountNumber;
    String creditNarration;
    String creditAccountBranchCode;
    String debitAccountBranchCode;
    String creditAccountType;
    String currency;
    String debitAccountName;
    String debitAccountNumber;
    String debitNarration;
    String debitAccountType;
    String initBranchCode;
    String paymentReference;
}
