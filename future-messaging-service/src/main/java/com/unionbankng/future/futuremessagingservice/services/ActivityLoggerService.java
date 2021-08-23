package com.unionbankng.future.futuremessagingservice.services;
import com.unionbankng.future.futuremessagingservice.entities.ActivityLog;
import com.unionbankng.future.futuremessagingservice.repositories.ActivityLoggerRepository;
import com.unionbankng.future.futuremessagingservice.util.App;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityLoggerService {

    private final ActivityLoggerRepository activityLoggerRepository;
    private final App app;

    public ActivityLog log(ActivityLog log){
       return activityLoggerRepository.save(log);
    }

}
