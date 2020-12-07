package com.unionbankng.future.futurejobservice.services;
import com.unionbankng.future.authorizationserver.grpc.SidekiqUserDetailServiceGrpc;
import com.unionbankng.future.authorizationserver.grpc.UserDetailRequest;
import com.unionbankng.future.authorizationserver.grpc.UserDetailResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @GrpcClient("authService")
    private SidekiqUserDetailServiceGrpc.SidekiqUserDetailServiceBlockingStub sidekiqUserDetailServiceStub;

    public UserDetailResponse getUserById(Long userId){
        UserDetailRequest request = UserDetailRequest.newBuilder().
        setUserId(userId).build();
        UserDetailResponse response=sidekiqUserDetailServiceStub.getUserDetail(request);
       return response;
    }
}

