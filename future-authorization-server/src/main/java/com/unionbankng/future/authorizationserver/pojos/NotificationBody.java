package com.unionbankng.future.authorizationserver.pojos;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

public @Data
@Builder
class NotificationBody implements Serializable {

    private static final long serialVersionUID = -295422703255886286L;
    protected String body;
    protected String subject;
    protected String recipient;
    protected String attachment;
    protected String redirect;
}