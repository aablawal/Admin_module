package com.unionbankng.future.futuremessagingservice.pojo;

import lombok.Data;

public @Data class UBNSmsRequest {

	private String mobileNo;
	private String message;
	private String source;
	private String accountNo;
	

}
