package com.unionbankng.future.futurejobservice.util;
import com.unionbankng.future.futurejobservice.pojos.EmailBody;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private static final String EMAIL_DESTINATION = "futureemailqueue";
    private final JmsTemplate jmsTemplate;
    public void sendEmail(EmailBody emailBody){
        jmsTemplate.convertAndSend(EMAIL_DESTINATION,emailBody);
    }


}
