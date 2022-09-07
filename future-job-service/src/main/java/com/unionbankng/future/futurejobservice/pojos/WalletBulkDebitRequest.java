package com.unionbankng.future.futurejobservice.pojos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;

public @Data class WalletBulkDebitRequest {

	private BigDecimal  totalAmountPlusCharges;
	private BigDecimal  totalAmount;
	private String walletId;
	private String currencyCode;
	private ArrayList<WalletDebitRequest> recipients;

	public WalletBulkDebitRequest() {}

}
