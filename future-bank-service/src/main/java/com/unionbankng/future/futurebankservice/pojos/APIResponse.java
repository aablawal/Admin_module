package com.unionbankng.future.futurebankservice.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIResponse<T> {
    private String message;
    private boolean success;
    private T payload;
}
