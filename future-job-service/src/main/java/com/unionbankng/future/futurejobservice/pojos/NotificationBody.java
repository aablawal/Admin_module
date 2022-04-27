package com.unionbankng.future.futurejobservice.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class NotificationBody implements Serializable {

    private static final long serialVersionUID = -295422703255886286L;
    protected String body;
    protected String subject;
    protected Long recipient;
    protected String recipientEmail;
    protected String recipientName;
    protected String attachment;
    protected String action;
    protected String channel;
    protected String actionType;
    protected String priority;
    protected String topic;
}