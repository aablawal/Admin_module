package com.unionbankng.future.learn.pojo;

import com.unionbankng.future.learn.enums.BlobType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadMediaFromURLRequest {
    private String url;
    private BlobType type;
    private String filename;
}
