package com.unionbankng.future.learn.pojo;

import com.unionbankng.future.futureutilityservice.grpcserver.StreamingLocatorResponse;
import io.grpc.stub.StreamObserver;

public class StreamingLocatorObserver implements StreamObserver<StreamingLocatorResponse> {


    @Override
    public void onNext(StreamingLocatorResponse streamingLocatorResponse) {
        System.out.println(
                "File upload status :: " + streamingLocatorResponse
        );
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {

    }

}