package com.unionbankng.future.futureutilityservice.pojos;

import com.unionbankng.future.futureutilityservice.enums.BlobType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UploadMediaFromURLRequest{
    private String url;
    private BlobType type;
    private String filename;
}
