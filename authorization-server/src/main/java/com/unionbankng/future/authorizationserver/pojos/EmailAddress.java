package com.unionbankng.future.authorizationserver.pojos;

import com.unionbankng.future.authorizationserver.enums.RecipientType;
import lombok.Builder;
import lombok.Data;

@Builder
public @Data class EmailAddress {

    protected String displayName;
    protected String email;
    protected RecipientType recipientType;

}