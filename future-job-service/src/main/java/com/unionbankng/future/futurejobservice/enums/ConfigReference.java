package com.unionbankng.future.futurejobservice.enums;

public enum ConfigReference {

    VAT_RATE,
    UBN_INCOME,
    TOTAL_JOBS,
    TOTAL_JOBS_COMPLETED,
    TOTAL_JOBS_REJECTED;
    public String value() {
        return name();
    }

    public static ConfigReference fromValue(String v) {
        return valueOf(v);
    }

}