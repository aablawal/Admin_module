package com.unionbankng.future.futuremessagingservice.pojo;

import lombok.Data;

public @Data class EmailAttachment {

    protected String cid;
    protected String content;
    protected boolean inline;
    protected String mime;
    protected String name;

}
