package com.unionbankng.future.futuremessagingservice.jmslisteners;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.futuremessagingservice.interfaces.EmailProvider;
import com.unionbankng.future.futuremessagingservice.pojos.EmailBody;
import com.unionbankng.future.futuremessagingservice.pojos.NotificationBody;
import com.unionbankng.future.futuremessagingservice.services.NotificationService;
import com.unionbankng.future.futuremessagingservice.smsandemailproviders.UnionEmailProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

import java.util.function.Consumer;


@Component
@RequiredArgsConstructor
public class NotificationListener {

    private static final String QUEUE_NAME = "futurenotificationqueue";
    private final NotificationService notificationService;
    private final Logger logger = LoggerFactory.getLogger(NotificationListener.class);
    private final ObjectMapper mapper;


    @Bean
    Consumer<String> sendNotification(){

        return request ->{
            try {
                NotificationBody notificationBody = mapper.readValue(request, NotificationBody.class);
                logger.info("Notification message Received: {}", notificationBody.getSubject());
                notificationService.pushNotification(Long.valueOf(0),notificationBody);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
