package com.unionbankng.future.futuremessagingservice.services;
import com.unionbankng.future.authorizationserver.grpc.SidekiqUserDetailServiceGrpc;
import com.unionbankng.future.authorizationserver.grpc.UserDetailRequest;
import com.unionbankng.future.authorizationserver.grpc.UserDetailResponse;
import com.unionbankng.future.futuremessagingservice.pojos.User;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @GrpcClient("authService")
    private SidekiqUserDetailServiceGrpc.SidekiqUserDetailServiceBlockingStub sidekiqUserDetailServiceStub;
    Logger logger = LoggerFactory.getLogger(UserService.class);

    public User getUserById(Long userId){
        UserDetailRequest request = UserDetailRequest.newBuilder().
        setUserId(userId).build();
        UserDetailResponse response=sidekiqUserDetailServiceStub.getUserDetail(request);
         User user = new User();
         user.setId(response.getId());
         user.setUuid(response.getUuid());
         user.setFullName(response.getFullName());
         user.setCountry(response.getCountry());
         user.setAddress(response.getAddress());
         user.setEmail(user.getEmail());
         user.setPhoneNumber(response.getPhoneNumber());
         user.setStateOfResidence(response.getStateOfResidence());
         user.setUsername(user.getUsername());
         user.setImg(user.getImg());
         user.setAccountName(user.getAccountName());
         user.setAccountNumber(user.getAccountNumber());
         user.setIsEnabled(user.getIsEnabled());
         user.setCreatedAt(user.getCreatedAt());

         logger.info(user.toString());
       return user;
    }
}

