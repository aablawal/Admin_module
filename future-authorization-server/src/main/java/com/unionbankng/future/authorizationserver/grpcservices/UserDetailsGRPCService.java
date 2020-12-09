package com.unionbankng.future.authorizationserver.grpcservices;
import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.grpc.SidekiqUserDetailServiceGrpc;
import com.unionbankng.future.authorizationserver.grpc.UserDetailRequest;
import com.unionbankng.future.authorizationserver.grpc.UserDetailResponse;
import com.unionbankng.future.authorizationserver.services.UserService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class UserDetailsGRPCService extends SidekiqUserDetailServiceGrpc.SidekiqUserDetailServiceImplBase {

    private final UserService service;

    public void getUserDetail(UserDetailRequest request, StreamObserver<UserDetailResponse> responseObserver)
    {
        try {
            User user = service.findById(request.getUserId()).orElse(null);
            UserDetailResponse response = null;
            if (user != null) {

                response = UserDetailResponse.newBuilder()
                        .setId(user.getId())
                        .setUuid(user.getUuid()==null?"":user.getUmid())
                        .setUmid(user.getUmid())
                        .setUsername(user.getUsername())
                        .setAddress(user.getUserAddress() == null ? "" : user.getUserAddress())
                        .setFirstName(user.getFirstName())
                        .setLastName(user.getLastName())
                        .setFullName(user.getFirstName() + " " + user.getLastName())
                        .setAccountNumber(user.getAccountNumber() == null ? "" : user.getAccountNumber())
                        .setAccountName(user.getAccountName() == null ? "" : user.getAccountName())
                        .setAuthProvider(user.getAuthProvider().name())
                        .setCountry(user.getCountry() == null ? "" : user.getCountry())
                        .setStateOfResidence(user.getStateOfResidence() == null ? "" : user.getStateOfResidence())
                        .setPhoneNumber(user.getPhoneNumber())
                        .setIsEnabled(user.getIsEnabled())
                        .setUpdatedAt(user.getDateUpdated().toString())
                        .setCreatedAt(user.getCreatedAt().toString())
                        .setDateOfBirth(user.getDateOfBirth() == null ? "" : user.getDateOfBirth().toString())
                        .setDialingCode(user.getDialingCode())
                        .setEmail(user.getEmail())
                        .setImg(user.getImg() == null ? "" : user.getImg())
                        .build();


            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
