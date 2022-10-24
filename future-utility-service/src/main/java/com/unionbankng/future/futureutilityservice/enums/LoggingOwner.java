package com.unionbankng.future.futureutilityservice.enums;

public enum LoggingOwner {

    JOB_SERVICE,
    WALLET_SERVICE,
    AUTH_SERVICE,
    BANK_SERVICE;

    public String value() {
        return name();
    }

    public static LoggingOwner fromValue(String v) {
        return valueOf(v);
    }

}