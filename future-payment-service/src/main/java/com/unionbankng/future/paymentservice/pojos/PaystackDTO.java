package com.unionbankng.future.paymentservice.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaystackDTO {
    private Long id;
    private BigDecimal amount;
    private String currency;
    private String transaction_date;
    private String status;
    private String reference;
    private String domain;
    private String gateway_response;
    private String message;
    private String channel;
    private String ip_address;
    private String fees;
    private String paid_at;
}
