package com.unionbankng.future.paymentservice.pojos;

import lombok.Data;

@Data
public class InterswitchSDKResponse {

    private String url;

    private String txnref;

    private String resp;

    private String desc;

    private String apprAmt;

    private String amount;

    private String payRef;

    private String cardNum;

    private String mac;

}
