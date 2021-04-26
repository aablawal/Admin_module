package com.unionbankng.future.futuremessagingservice.pojos;

import java.io.Serializable;
import java.util.List;

import ch.qos.logback.core.joran.action.Action;
import lombok.Data;


public @Data class EmailBody implements Serializable {

    private static final long serialVersionUID = -295422703255886286L;

    protected List<EmailAttachment> attachments;
    protected String body;
    protected String footer;
    protected List<EmailAddress> recipients;
    protected EmailAddress sender;
    protected String subject;


}