package com.unionbankng.future.futureloanservice.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

public @Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
class NotificationBody implements Serializable {

    private static final long serialVersionUID = -295422703255886286L;
    protected String body;
    protected String subject;
    protected Long recipient;
    protected String attachment;
    protected String action;
    protected String channel;
    protected String actionType;
    protected String priority;
    protected String topic;
}