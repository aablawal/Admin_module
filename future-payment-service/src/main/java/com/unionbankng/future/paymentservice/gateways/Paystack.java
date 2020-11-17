package com.unionbankng.future.paymentservice.gateways;

import com.unionbankng.future.learn.grpcserver.CoursePaymentRequest;
import com.unionbankng.future.learn.grpcserver.CoursePaymentResponse;
import com.unionbankng.future.learn.grpcserver.CoursePaymentServiceGrpc;
import com.unionbankng.future.learn.grpcserver.Status;
import com.unionbankng.future.paymentservice.entities.PaymentReference;
import com.unionbankng.future.paymentservice.enums.PaymentGateway;
import com.unionbankng.future.paymentservice.enums.PaymentStatus;
import com.unionbankng.future.paymentservice.enums.ProductPayingFor;
import com.unionbankng.future.paymentservice.interfaces.IPayment;
import com.unionbankng.future.paymentservice.pojos.PaystackTransaction;
import com.unionbankng.future.paymentservice.services.PaymentReferenceService;
import com.unionbankng.future.paymentservice.utils.PaystackAuthValidator;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class Paystack implements IPayment {

    Logger logger = LoggerFactory.getLogger(Paystack.class);

    private final PaymentReferenceService paymentReferenceService;

    @GrpcClient("learnService")
    private CoursePaymentServiceGrpc.CoursePaymentServiceBlockingStub coursePaymentServiceBlockingStub;


    public void setStub(CoursePaymentServiceGrpc.CoursePaymentServiceBlockingStub coursePaymentServiceBlockingStub){
        this.coursePaymentServiceBlockingStub = coursePaymentServiceBlockingStub;
    }

    @Override
    public Boolean validatePayment(String... args) {

        try {

            return PaystackAuthValidator.isTokenValid(args[0], args[1], args[2]);


        } catch (Exception e) {

            logger.info("An error occurred while validating paystack payment :{}", e.getMessage());
        }

        return false;
    }

    @Override
    public ResponseEntity<String> completePayment(Object request) {

        PaystackTransaction transaction = (PaystackTransaction) request;

        //check for duplicate
        if (paymentReferenceService.existByPaymentGatewayAndReference(PaymentGateway.PAYSTACK, transaction.getData().getReference())) {
            logger.info("Oops!!! this payment reference already exists, ref is :",transaction.getData().getReference());
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }


        //get meta data
        String userUUID = transaction.getData().getMetadata().getUserUUID();
        Long entityId = transaction.getData().getMetadata().getEntityId();
        ProductPayingFor productPayingFor = transaction.getData().getMetadata().getProductPayingFor();
        String email = transaction.getData().getMetadata().getEmail();
        BigDecimal amount = transaction.getData().getMetadata().getAmount();

        //do payment

        if (!productPayingFor.equals(ProductPayingFor.COURSE)) {
            logger.info("Payment not supported");
            return new ResponseEntity<>("Request is good", HttpStatus.OK);
        }


        CoursePaymentRequest coursePaymentRequest = CoursePaymentRequest.newBuilder().setCourseId(entityId.intValue())
                .setAmountPaid(amount.doubleValue()).setEmail(email).setPaymentReference(transaction.getData().getReference())
                .setUserUUID(userUUID).build();

        CoursePaymentResponse coursePaymentResponse = coursePaymentServiceBlockingStub.payForCourse(coursePaymentRequest);


        if (coursePaymentResponse.getStatus().equals(Status.FAILED)) {
            logger.info("Payment for course failed with status : {}", coursePaymentResponse.getStatus());
            return new ResponseEntity<>("Something went wrong", HttpStatus.EXPECTATION_FAILED);
        }

        PaymentReference paymentReference = new PaymentReference();
        paymentReference.setAmount(amount.doubleValue());
        paymentReference.setPaymentGateway(PaymentGateway.PAYSTACK);
        paymentReference.setRef(transaction.getData().getReference());
        paymentReference.setStatus(PaymentStatus.SUCCESS);
        paymentReference.setUserUUID(userUUID);

        paymentReferenceService.save(paymentReference);


        logger.info("Payment for course status is {}, this not required to fail", coursePaymentResponse.getStatus());

        return new ResponseEntity<>("Request is good", HttpStatus.OK);


    }
}
