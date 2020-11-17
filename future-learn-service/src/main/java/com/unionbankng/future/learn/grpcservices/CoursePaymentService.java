package com.unionbankng.future.learn.grpcservices;

import com.unionbankng.future.learn.grpcserver.CoursePaymentResponse;
import com.unionbankng.future.learn.grpcserver.CoursePaymentServiceGrpc;
import com.unionbankng.future.learn.services.UserCourseService;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class CoursePaymentService extends CoursePaymentServiceGrpc.CoursePaymentServiceImplBase {

    private final UserCourseService userCourseService;


    public void payForCourse(com.unionbankng.future.learn.grpcserver.CoursePaymentRequest request,
                             io.grpc.stub.StreamObserver<com.unionbankng.future.learn.grpcserver.CoursePaymentResponse> responseObserver) {

        CoursePaymentResponse response = userCourseService.enrollForPaidCourse(request);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
        
    }
}
