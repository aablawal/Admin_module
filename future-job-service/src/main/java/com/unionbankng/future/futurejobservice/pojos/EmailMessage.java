package com.unionbankng.future.futurejobservice.pojos;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

public @Data
@Builder
class EmailMessage implements Serializable {

    private static final long serialVersionUID = -295422703255886286L;
    protected List<EmailBody.EmailAttachment> attachments;
    protected String body;
    protected String subject;
    protected EmailAddress sender;
    protected String recipient;
}