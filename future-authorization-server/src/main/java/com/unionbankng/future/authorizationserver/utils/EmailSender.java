package com.unionbankng.future.authorizationserver.utils;

import com.unionbankng.future.authorizationserver.pojos.EmailBody;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private static final String EMAIL_DESTINATION = "emailqueue";
    private final JmsTemplate jmsTemplate;

    public void sendEmail(EmailBody emailBody){
        jmsTemplate.convertAndSend(EMAIL_DESTINATION,emailBody);
    }


}
