package com.unionbankng.future.futurebankservice;

import com.unionbankng.future.futurebankservice.pojos.APIResponse;
import com.unionbankng.future.futurebankservice.pojos.SidekiqBVNValidationResponse;
import com.unionbankng.future.futurebankservice.pojos.ValidateBvnRequest;
import com.unionbankng.future.futurebankservice.pojos.ValidateBvnResponse;
import com.unionbankng.future.futurebankservice.services.BvnValidationService;
import com.unionbankng.future.futurebankservice.services.UBNAccountAPIServiceHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import retrofit2.Response;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BVNTest extends AbstractTest {

    @Autowired
    UBNAccountAPIServiceHandler ubnAccountAPIServiceHandler;

    @Autowired
    BvnValidationService bvnValidationService;


    @Test
    public void testValidateWrongBVN() throws IOException {

        ValidateBvnRequest validateBvnRequest = new ValidateBvnRequest();
        validateBvnRequest.setBvn("11111111111");

        Response<ValidateBvnResponse> response = ubnAccountAPIServiceHandler.validateCustomerBVN(validateBvnRequest);

        assertEquals("01",response.body().getResponseCode());
        assertEquals(200,response.code());

    }

    @Test
    public void testBVNValidationSuccess() throws IOException {

        ValidateBvnRequest validateBvnRequest = new ValidateBvnRequest();
        validateBvnRequest.setBvn("22234197239");

        Response<ValidateBvnResponse> response = ubnAccountAPIServiceHandler.validateCustomerBVN(validateBvnRequest);

        assertEquals("00",response.body().getResponseCode());
        assertEquals(200,response.code());

    }

    @Test
    public void verifyCustomerBvnWrongDOBTest() throws IOException {

        ValidateBvnRequest validateBvnRequest = new ValidateBvnRequest();
        validateBvnRequest.setBvn("22234197239");

        ResponseEntity<APIResponse<SidekiqBVNValidationResponse>> response = bvnValidationService.verifyCustomerBVN("22234197239","12 JUNE 2010");

        assertEquals(400,response.getStatusCodeValue());

    }

    @Test
    public void verifyCustomerBvnSuccessTest() throws IOException {

        ValidateBvnRequest validateBvnRequest = new ValidateBvnRequest();
        validateBvnRequest.setBvn("22234197239");

        ResponseEntity<APIResponse<SidekiqBVNValidationResponse>> response = bvnValidationService.verifyCustomerBVN("22234197239","25-Dec-94");

        assertEquals(200,response.getStatusCodeValue());
        assertEquals(Boolean.TRUE,response.getBody().isSuccess());

    }


}
