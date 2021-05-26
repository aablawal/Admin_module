package com.unionbankng.future.futurejobservice.util;
import com.unionbankng.future.futurejobservice.pojos.NotificationBody;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationSender {

    private static final String EMAIL_DESTINATION = "futurenotificationqueue";
    private final JmsTemplate jmsTemplate;

    public void pushNotification(NotificationBody notificationBody){
        jmsTemplate.convertAndSend(EMAIL_DESTINATION,notificationBody);
    }

}
