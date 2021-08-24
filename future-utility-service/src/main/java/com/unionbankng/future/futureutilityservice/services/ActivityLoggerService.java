package com.unionbankng.future.futureutilityservice.services;
import com.unionbankng.future.futureutilityservice.entities.ActivityLog;
import com.unionbankng.future.futureutilityservice.repositories.ActivityLoggerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityLoggerService {

    private final ActivityLoggerRepository activityLoggerRepository;

    public ActivityLog log(ActivityLog log){
       return activityLoggerRepository.save(log);
    }

}
