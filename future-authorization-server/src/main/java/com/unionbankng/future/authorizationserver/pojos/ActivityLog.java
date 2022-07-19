package com.unionbankng.future.authorizationserver.pojos;

import com.unionbankng.future.authorizationserver.enums.LoggingOwner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public @Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
class ActivityLog implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;
    protected LoggingOwner owner;
    protected String requestObject;
    protected String responseObject;
    protected String userId;
    protected String username;
    protected String device;
    protected String ipAddress;
    protected String description;
    protected String date;
}
