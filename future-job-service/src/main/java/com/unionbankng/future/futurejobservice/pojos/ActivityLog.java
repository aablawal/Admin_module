package com.unionbankng.future.futurejobservice.pojos;

import com.unionbankng.future.futurejobservice.enums.LoggingOwner;
import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ActivityLog implements Serializable {

    protected LoggingOwner owner;
    protected String requestObject;
    protected String responseObject;
    protected String userId;
    protected String username;
    protected String device;
    protected String ipAddress;
    protected String description;
}
