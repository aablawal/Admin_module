package com.unionbankng.future.learn.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StreamingLocatorResponse {

    private String locatorName;
    private String assetName;
    private Boolean success;
}