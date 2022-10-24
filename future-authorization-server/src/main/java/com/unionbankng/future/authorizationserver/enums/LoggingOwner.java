package com.unionbankng.future.authorizationserver.enums;

public enum LoggingOwner {

    JOB_SERVICE,
    AUTH_SERVICE,
    BANK_SERVICE;

    public String value() {
        return name();
    }

    public static LoggingOwner fromValue(String v) {
        return valueOf(v);
    }

}
