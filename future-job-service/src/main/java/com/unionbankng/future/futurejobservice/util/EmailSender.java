package com.unionbankng.future.futurejobservice.util;
import com.unionbankng.future.futurejobservice.pojos.EmailBody;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private static final String EMAIL_DESTINATION = "kulaemailqueue";
    private final JmsTemplate jmsTemplate;
    public void sendEmail(EmailBody emailBody){
        try {
            CachingConnectionFactory connectionFactory = (CachingConnectionFactory) jmsTemplate.getConnectionFactory();
            connectionFactory.setCacheProducers(false);
            jmsTemplate.convertAndSend(EMAIL_DESTINATION, emailBody);
        }catch (Exception ex){
            System.out.println("Unable to send Email");
            ex.printStackTrace();
        }
    }
}
