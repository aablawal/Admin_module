package com.unionbankng.future.futurebankservice.pojos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BVNValidationResponse {
	 private String statusCode;
	 private String statusMessage;
	 private String accountText;
}