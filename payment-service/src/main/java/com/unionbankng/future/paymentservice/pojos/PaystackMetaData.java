package com.unionbankng.future.paymentservice.pojos;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaystackMetaData {
    private String walletId;
    private String email;
    private String fullname;
    private BigDecimal amount;
	private List<CustomFields> custom_fields;
	
}
