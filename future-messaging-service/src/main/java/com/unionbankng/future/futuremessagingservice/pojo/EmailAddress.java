package com.unionbankng.future.futuremessagingservice.pojo;

import com.unionbankng.future.futuremessagingservice.enums.RecipientType;
import lombok.Builder;
import lombok.Data;

@Builder
public @Data class EmailAddress {

    protected String displayName;
    protected String email;
    protected RecipientType recipientType;

}