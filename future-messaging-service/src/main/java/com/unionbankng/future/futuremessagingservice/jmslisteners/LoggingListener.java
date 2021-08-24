package com.unionbankng.future.futuremessagingservice.jmslisteners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.futuremessagingservice.entities.ActivityLog;
import com.unionbankng.future.futuremessagingservice.interfaces.EmailProvider;
import com.unionbankng.future.futuremessagingservice.pojos.EmailBody;
import com.unionbankng.future.futuremessagingservice.services.ActivityLoggerService;
import com.unionbankng.future.futuremessagingservice.smsandemailproviders.UnionEmailProvider;
import com.unionbankng.future.futuremessagingservice.util.App;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Calendar;

@Component
@RequiredArgsConstructor
public class LoggingListener {

    private static final String QUEUE_NAME = "kulaloggingqueue";
    private final ActivityLoggerService activityLoggerService;
    private final ObjectMapper mapper;
    private final App app;


    @JmsListener(destination = QUEUE_NAME, containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(String json) throws JsonProcessingException {
        ActivityLog log = mapper.readValue(json, ActivityLog.class);
        ActivityLog savedLog= activityLoggerService.log(log);
        app.print(savedLog);
    }


}
