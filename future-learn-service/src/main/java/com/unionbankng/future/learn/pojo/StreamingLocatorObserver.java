package com.unionbankng.future.learn.pojo;

import com.unionbankng.future.futureutilityservice.grpcserver.StreamingLocatorResponse;

public class StreamingLocatorObserver implements io.grpc.stub.StreamObserver<com.unionbankng.future.futureutilityservice.grpcserver.StreamingLocatorResponse> {


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