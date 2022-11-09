package com.unionbankng.future.futureutilityservice.grpcservices;

import com.unionbankng.future.futureutilityservice.grpcserver.ActivityLogDto;
import com.unionbankng.future.futureutilityservice.grpcserver.ActivityLogResponse;
import com.unionbankng.future.futureutilityservice.grpcserver.ActivityLogSearchRequest;
import com.unionbankng.future.futureutilityservice.grpcserver.ActivityLogServiceGrpc;
import com.unionbankng.future.futureutilityservice.services.ActivityLoggerService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class ActivityLogService extends ActivityLogServiceGrpc.ActivityLogServiceImplBase {

    private final ActivityLoggerService activityLogService;

    @Override
    public void getActivityLogs(ActivityLogSearchRequest request, StreamObserver<ActivityLogResponse> responseObserver) {
        responseObserver.onNext(activityLogService.getActivityLogs(request));
        responseObserver.onCompleted();
    }
}
