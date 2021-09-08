package com.unionbankng.future.authorizationserver.utils;

import com.unionbankng.future.authorizationserver.pojos.EmailBody;
import lombok.RequiredArgsConstructor;
import org.apache.maven.plugin.lifecycle.Execution;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private static final String EMAIL_DESTINATION = "kulaemailqueue";
    private final JmsTemplate jmsTemplate;
    private final App app;

    public void sendEmail(EmailBody emailBody){
        try {
            app.print(emailBody);
            CachingConnectionFactory connectionFactory = (CachingConnectionFactory) jmsTemplate.getConnectionFactory();
            connectionFactory.setCacheProducers(false);
            jmsTemplate.convertAndSend(EMAIL_DESTINATION, emailBody);
        }catch (Exception ex){
            System.out.println("Unable to send Email");
            ex.printStackTrace();
        }
    }


}
