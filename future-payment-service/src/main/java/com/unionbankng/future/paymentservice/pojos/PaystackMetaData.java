package com.unionbankng.future.paymentservice.pojos;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.unionbankng.future.paymentservice.enums.ProductPayingFor;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaystackMetaData {
    private String userUUID;
    private String email;
    private Long entityId;
    private ProductPayingFor productPayingFor;
    private BigDecimal amount;
	private List<CustomFields> custom_fields;
	
}
