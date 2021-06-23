package com.unionbankng.future.futuremessagingservice.services;
import com.unionbankng.future.futuremessagingservice.pojos.EmailBody;
import com.unionbankng.future.futuremessagingservice.pojos.EmailMessage;
import com.unionbankng.future.futuremessagingservice.util.EmailSender;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@RefreshScope
@Service
@RequiredArgsConstructor
public class EmailService {


    @Value("${email.sender}")
    private String emailSenderAddress;

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final EmailSender emailSender;


    public void sendEmail(EmailMessage message) {
        EmailBody emailBody = new EmailBody();
        emailBody.setBody(message.getBody());
        emailBody.setAttachments(message.getAttachments());
        emailBody.setSubject(message.getSubject());
        emailBody.setSender(message.getSender());
        emailSender.sendEmail(emailBody);
    }
}
