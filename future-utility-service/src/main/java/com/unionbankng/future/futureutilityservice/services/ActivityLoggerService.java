package com.unionbankng.future.futureutilityservice.services;
import com.unionbankng.future.futureutilityservice.entities.ActivityLog;
import com.unionbankng.future.futureutilityservice.grpcserver.ActivityLogDto;
import com.unionbankng.future.futureutilityservice.grpcserver.ActivityLogResponse;
import com.unionbankng.future.futureutilityservice.grpcserver.ActivityLogSearchRequest;
import com.unionbankng.future.futureutilityservice.pojos.APIResponse;
import com.unionbankng.future.futureutilityservice.repositories.ActivityLoggerRepository;
import liquibase.pro.packaged.A;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityLoggerService {

    private final ActivityLoggerRepository activityLoggerRepository;

    public ActivityLog log(ActivityLog log){
       return activityLoggerRepository.save(log);
    }

    public ActivityLogResponse getActivityLogs(ActivityLogSearchRequest request) {
        Sort sort = request.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(request.getSortBy()).ascending()
                : Sort.by(request.getSortBy()).descending();

        Pageable pageable = PageRequest.of(request.getPageNo(), request.getPageSize(), sort);

        Page<ActivityLog> activityLogs = activityLoggerRepository.activityLogSearch(pageable, request.getQuery());
        List<ActivityLog> activityLogList = activityLogs.getContent();
        List<ActivityLogDto> list = activityLogList.stream().map(x  -> ActivityLogDto.newBuilder()
                .setDescription(x.getDescription())
                .setDevice(x.getDevice())
                .setIpAddress(x.getIpAddress())
                .setUserId(x.getUserId())
                .setUsername(x.getUsername())
                .build()
        ).collect(Collectors.toList());

        return ActivityLogResponse.newBuilder()
                .addAllContent(list)
                .setPageNo(activityLogs.getNumber() + 1)
                .setPageSize(activityLogs.getSize())
                .setTotalElements(activityLogs.getTotalElements())
                .setTotalPages(activityLogs.getTotalPages())
                .setLast(activityLogs.isLast())
                .build();
    }
}
