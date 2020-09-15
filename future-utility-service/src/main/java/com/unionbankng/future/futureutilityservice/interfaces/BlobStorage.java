package com.unionbankng.future.futureutilityservice.interfaces;


import com.unionbankng.future.futureutilityservice.enums.BlobType;

public interface BlobStorage {

    String upload (byte[] fileByte, BlobType blobType, String fileName);
    void delete (String identifier, BlobType blobType);
}
