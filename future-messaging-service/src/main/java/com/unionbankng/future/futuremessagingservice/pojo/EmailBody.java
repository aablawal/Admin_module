package com.unionbankng.future.futuremessagingservice.pojo;

import java.util.List;

import lombok.Data;


public @Data class EmailBody {

    protected List<EmailAttachment> attachments;
    protected String body;
    protected String footer;
    protected List<EmailAddress> recipients;
    protected EmailAddress sender;
    protected String subject;

}