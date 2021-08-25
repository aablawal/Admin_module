package com.unionbankng.future.authorizationserver.utils;

import com.unionbankng.future.authorizationserver.pojos.EmailBody;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private static final String EMAIL_DESTINATION = "kulaemailqueue";
    private final JmsTemplate jmsTemplate;
    private final App app;

    public void sendEmail(EmailBody emailBody){
        app.print(emailBody);
        jmsTemplate.convertAndSend(EMAIL_DESTINATION,emailBody);
    }


}
