package com.unionbankng.future.paymentservice.pojos;

import java.util.List;

import com.unionbankng.marcus.entities.PaystackRef;

import lombok.Data;

@Data
public class PaystackRefResponse {

	private boolean success;
	private int start;
	private int limit;
	private int size;
	private List<PaystackRef> payload;
	
}
