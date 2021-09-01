package com.unionbankng.future.futurejobservice.util;
import com.unionbankng.future.futurejobservice.enums.LoggingOwner;
import com.unionbankng.future.futurejobservice.pojos.ActivityLog;
import com.unionbankng.future.futurejobservice.pojos.EmailBody;
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
            log.setOwner(LoggingOwner.JOB_SERVICE);
            log.setDevice(app.getClientDevice());
            log.setIpAddress(app.getClientMACAddress());
            jmsTemplate.convertAndSend(LOGGING_QUEUE_NAME, log);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}