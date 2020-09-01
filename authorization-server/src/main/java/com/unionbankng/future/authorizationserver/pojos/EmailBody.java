package com.unionbankng.future.authorizationserver.pojos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

public @Data
@Builder
class EmailBody {

    protected List<EmailAttachment> attachments;
    protected String body;
    protected String footer;
    protected List<EmailAddress> recipients;
    protected EmailAddress sender;
    protected String subject;

}