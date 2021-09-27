package com.unionbankng.future.futureutilityservice.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class APIResponse<T> {
    private String message;
    private boolean success;
    private T payload;
}
