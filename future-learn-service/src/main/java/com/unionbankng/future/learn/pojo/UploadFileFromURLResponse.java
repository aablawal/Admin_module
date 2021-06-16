package com.unionbankng.future.learn.pojo;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class UploadFileFromURLResponse {
    @NotNull
    private String streamingLocatorName;
    @NotNull
    private String outputAssetName;
}
