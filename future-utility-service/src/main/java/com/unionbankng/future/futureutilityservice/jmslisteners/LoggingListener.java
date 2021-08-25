package com.unionbankng.future.futureutilityservice.jmslisteners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.futureutilityservice.entities.ActivityLog;
import com.unionbankng.future.futureutilityservice.services.ActivityLoggerService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoggingListener {

    private static final String QUEUE_NAME = "kulaloggingqueue";
    private final ActivityLoggerService activityLoggerService;
    private final ObjectMapper mapper;


    @JmsListener(destination = QUEUE_NAME, containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(String json) throws JsonProcessingException {
        ActivityLog log = mapper.readValue(json, ActivityLog.class);
        ActivityLog savedLog= activityLoggerService.log(log);
    }


}
