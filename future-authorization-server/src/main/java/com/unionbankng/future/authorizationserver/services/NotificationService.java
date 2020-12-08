package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.pojos.NotificationBody;
import com.unionbankng.future.authorizationserver.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository notificationRepository;

    public String updateUserMID(String id){
        return  id;
    }

    public NotificationBody pushNotification(NotificationBody notificationBody){

        return  notificationBody;
    }




}
