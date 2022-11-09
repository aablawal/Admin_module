package com.unionbankng.future.authorizationserver.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.AdminResponse;
import com.unionbankng.future.authorizationserver.utils.App;
import com.unionbankng.future.futureutilityservice.grpcserver.ActivityLogResponse;
import com.unionbankng.future.futureutilityservice.grpcserver.ActivityLogSearchRequest;
import com.unionbankng.future.futureutilityservice.grpcserver.ActivityLogServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityLogService {

    Logger logger = LoggerFactory.getLogger(ActivityLogService.class);



    private final App app;


    @GrpcClient("utilityService")
    private  ActivityLogServiceGrpc.ActivityLogServiceBlockingStub activityLogServiceBlockingStub;

    public ResponseEntity<APIResponse> search(int pageNo, int pageSize, String sortBy, String sortDir, String q) throws JsonProcessingException {
        ActivityLogSearchRequest activityLogSearchRequest = ActivityLogSearchRequest.newBuilder()
                .setPageNo(pageNo).setPageSize(pageSize).setSortBy(sortBy).setSortDir(sortDir).setQuery(q).build();

       ActivityLogResponse response =  activityLogServiceBlockingStub.getActivityLogs(activityLogSearchRequest);
        AdminResponse adminResponse = new AdminResponse();

        adminResponse.setContent(response.getContentList());

        adminResponse.setPageNo(response.getPageNo());
        adminResponse.setPageSize(response.getPageSize());
        adminResponse .setTotalElements( (long) response.getTotalElements());
        adminResponse.setTotalPages(response.getTotalPages());
        adminResponse.setLast(response.getLast());

//        logger.info( "Response wanted  "  +String.valueOf(adminResponse));
//        String ans = String.valueOf(response);
//
//        String data = app.getMapper().writeValueAsString(response);
//        ObjectMapper objectMapper = new ObjectMapper();






        return ResponseEntity.ok(new APIResponse<>("Request successful", true, adminResponse));
    }
}
