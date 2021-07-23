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





}
