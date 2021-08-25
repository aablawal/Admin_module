package com.unionbankng.future.paymentservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.learn.grpcserver.CoursePaymentRequest;
import com.unionbankng.future.learn.grpcserver.CoursePaymentResponse;
import com.unionbankng.future.learn.grpcserver.CoursePaymentServiceGrpc;
import com.unionbankng.future.learn.grpcserver.Status;
import com.unionbankng.future.paymentservice.enums.ProductPayingFor;
import com.unionbankng.future.paymentservice.gateways.Paystack;
import com.unionbankng.future.paymentservice.pojos.PaystackData;
import com.unionbankng.future.paymentservice.pojos.PaystackMetaData;
import com.unionbankng.future.paymentservice.pojos.PaystackTransaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PaystackPaymentTest extends AbstractTest {


    @Autowired
    private ObjectMapper mapper;

    @Autowired
    Paystack paystack;



    private CoursePaymentServiceGrpc.CoursePaymentServiceBlockingStub coursePaymentServiceBlockingStub = Mockito
            .mock(CoursePaymentServiceGrpc.CoursePaymentServiceBlockingStub.class);

    @Override
    @Before
    public void setUp() {
        super.setUp();
        paystack.setStub(coursePaymentServiceBlockingStub);
    }

    @Test
    public void paystackWebhookValidationFailedTest() throws Exception {

        PaystackTransaction request = new PaystackTransaction();
        request.setData(new PaystackData());
        request.setEvent("Payment");

        String body = mapper.writeValueAsString(request);

        mvc.perform(MockMvcRequestBuilders.post("/api/webhooks/v1/paystack")
                .header("x-paystack-signature","this-is-just-a-test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());

    }


    @Test
    public void paystackWebhookSuccessTest()  {

        PaystackMetaData paystackMetaData = new PaystackMetaData();
        paystackMetaData.setAmount(BigDecimal.TEN);
        paystackMetaData.setEmail("okemedjbabs@gmail.com");
        paystackMetaData.setEntityId(1l);
        paystackMetaData.setProductPayingFor(ProductPayingFor.COURSE);
        paystackMetaData.setUserUUID("2222-22333f-fffffr-33333");

        PaystackData paystackData = new PaystackData();
        paystackData.setReference("11293sr");
        paystackData.setAmount(BigDecimal.TEN);
        paystackData.setCurrency("NGN");
        paystackData.setMetadata(paystackMetaData);

        PaystackTransaction request = new PaystackTransaction();
        request.setData(paystackData);
        request.setEvent("Payment");


        CoursePaymentResponse coursePaymentResponse = CoursePaymentResponse.newBuilder().setStatus(Status.SUCCESS).setSuccess(true).build();
        Mockito.when(coursePaymentServiceBlockingStub.payForCourse(any(CoursePaymentRequest.class))).thenAnswer(i -> coursePaymentResponse);


        ResponseEntity<String> responseEntity =  paystack.completePayment(request);

        Assert.assertEquals(200,responseEntity.getStatusCodeValue());


    }
}
