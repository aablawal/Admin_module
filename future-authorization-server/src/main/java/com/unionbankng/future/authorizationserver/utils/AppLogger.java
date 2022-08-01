package com.unionbankng.future.authorizationserver.utils;

import com.unionbankng.future.authorizationserver.enums.LoggingOwner;
import com.unionbankng.future.authorizationserver.pojos.ActivityLog;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppLogger {

    private static final String LOGGING_QUEUE_NAME = "kulaloggingqueue";
    private final JmsTemplate jmsTemplate;
    private final App app;

    public void log(ActivityLog log){
        try {
            log.setOwner(LoggingOwner.AUTH_SERVICE);
            log.setDevice(app.getClientDevice());
            log.setIpAddress(app.getClientMACAddress());
            jmsTemplate.convertAndSend(LOGGING_QUEUE_NAME, log);
            System.out.println("Log pushed to logger successfully");
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("Unable to push to logger queue");
        }
    }
}
