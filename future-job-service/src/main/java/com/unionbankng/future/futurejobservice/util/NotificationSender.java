package com.unionbankng.future.futurejobservice.util;
import com.unionbankng.future.futurejobservice.pojos.NotificationBody;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationSender {

    private static final String EMAIL_DESTINATION = "kulanotificationqueue";
    private final JmsTemplate jmsTemplate;

    public void pushNotification(NotificationBody notificationBody){
        try {
            jmsTemplate.convertAndSend(EMAIL_DESTINATION, notificationBody);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
