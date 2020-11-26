package com.unionbankng.future.futurebankservice;

import com.unionbankng.future.futurebankservice.pojos.*;
import com.unionbankng.future.futurebankservice.services.UBNNewAccountOpeningAPIServiceHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Response;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UBNAccountOpeningAPITest extends AbstractTest {

    @Autowired
    UBNNewAccountOpeningAPIServiceHandler ubnNewAccountOpeningAPIServiceHandler;

    @Test
    public void testIdCardTypes() throws IOException {


        Response<AccountIdTypesResponse> response = ubnNewAccountOpeningAPIServiceHandler.getSupportedIdTypesForAccount();

        System.out.println(response.toString());
        assertEquals(200,response.code());
        assertEquals("00",response.body().getStatusCode());



    }

    @Test
    public void testGetAccountProductTypes() throws IOException {

        AccountProductTypeRequest request = AccountProductTypeRequest.builder().adult(true).hasValidId(true).idCard("nin").build();

        Response<AccountProductTypeResponse> response = ubnNewAccountOpeningAPIServiceHandler.getAccountProductTypes(request);

        System.out.println(response.toString());
        assertEquals(200,response.code());
        assertEquals("00",response.body().getStatusCode());


    }

    @Test
    public void getAccountProductDetailsTest() throws IOException {


        Response<ProductDetailsResponse> response = ubnNewAccountOpeningAPIServiceHandler.getAccountProductDetails("SA_006");

        System.out.println(response.toString());
        assertEquals(200,response.code());
        assertEquals("00",response.body().getStatusCode());


    }

    @Test
    public void getCountriesForAccountTest() throws IOException {


        Response<UBNCountryResponse> response = ubnNewAccountOpeningAPIServiceHandler.getCountriesForAccount();

        System.out.println(response.toString());
        assertEquals(200,response.code());
        assertEquals("00",response.body().getStatusCode());


    }

    @Test
    public void getStatesByCountryForAccountTest() throws IOException {


        Response<UBNStateByCountryResponse> response = ubnNewAccountOpeningAPIServiceHandler.getStatesByCountryForAccount("NG");

        System.out.println(response.toString());
        assertEquals(200,response.code());
        assertEquals("00",response.body().getStatusCode());


    }


    @Test
    public void getCitiesByCountryAndStateForAccountTest() throws IOException {


        Response<UBNCitiesResponse> response = ubnNewAccountOpeningAPIServiceHandler.getCitiesByCountryAndStateForAccount("NG","OYO");

        System.out.println(response.toString());
        assertEquals(200,response.code());
        assertEquals("00",response.body().getStatusCode());


    }

    @Test
    public void getUBNBranchesTest() throws IOException {


        Response<UBNBranchesResponse> response = ubnNewAccountOpeningAPIServiceHandler.getUBNBranches();

        System.out.println(response.toString());
        assertEquals(200,response.code());
        assertEquals("00",response.body().getStatusCode());


    }

    @Test
    public void getUBNGenders() throws IOException {


        Response<AccountProductTypeResponse> response = ubnNewAccountOpeningAPIServiceHandler.getUBNGenders();

        System.out.println(response.toString());
        assertEquals(200,response.code());
        assertEquals("00",response.body().getStatusCode());


    }

    @Test
    public void getMaritalStatus() throws IOException {


        Response<AccountProductTypeResponse> response = ubnNewAccountOpeningAPIServiceHandler.getMaritalStatus();

        System.out.println(response.toString());
        assertEquals(200,response.code());
        assertEquals("00",response.body().getStatusCode());


    }

    @Test
    public void getCustomerTypesTest() throws IOException {


        Response<UBNCustomerTypeRequest> response = ubnNewAccountOpeningAPIServiceHandler.getCustomerTypes();

        System.out.println(response.toString());
        assertEquals(200,response.code());
        assertEquals("00",response.body().getStatusCode());


    }

    @Test
    public void getCustomersSegmentTest() throws IOException {


        Response<UBNCustomerSegmentResponse> response = ubnNewAccountOpeningAPIServiceHandler.getCustomersSegment();

        System.out.println(response.toString());
        assertEquals(200,response.code());
        assertEquals("00",response.body().getStatusCode());


    }

    @Test
    public void getDocumentTypesTest() throws IOException {


        Response<AccountProductTypeResponse> response = ubnNewAccountOpeningAPIServiceHandler.getDocumentTypes("SA_008");

        System.out.println(response.toString());
        assertEquals(200,response.code());
        assertEquals("00",response.body().getStatusCode());


    }










}
