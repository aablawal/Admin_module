package com.unionbankng.future.futurejobservice.pojos;

import lombok.Data;
import java.util.List;

@Data
public class WalletCreditDebitBulkResponse {

	private String message;
	private List<WalletDebitCreditResponse> data;
	private boolean success;


	public WalletCreditDebitBulkResponse(String message, boolean success, List<WalletDebitCreditResponse> data) {
		super();
		this.message = message;
		this.success = success;
		this.data = data;
	}
}
