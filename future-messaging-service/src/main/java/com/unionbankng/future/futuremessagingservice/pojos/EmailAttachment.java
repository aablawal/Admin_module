package com.unionbankng.future.futuremessagingservice.pojos;

import lombok.Data;

import java.io.Serializable;

public @Data class EmailAttachment  implements Serializable{

    private static final long serialVersionUID = -295422703255886286L;
    protected String cid;
    protected String content;
    protected boolean inline;
    protected String mime;
    protected String name;

}