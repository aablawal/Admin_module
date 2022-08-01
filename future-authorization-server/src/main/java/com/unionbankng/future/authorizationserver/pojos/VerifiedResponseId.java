package com.unionbankng.future.authorizationserver.pojos;

import lombok.Data;

@Data
public class VerifiedResponseId<T> {
    private String responseCode;
    private String description;
    private String verificationType;
    private String verificationStatus;
    private String transactionStatus;
    private String transactionReference;
    private String transactionDate;
    private String searchParameter;
    private T response;
    private FaceMatchResponse faceMatch;
    private String selfieImageUrl;
    private String idImageUrl;
}
