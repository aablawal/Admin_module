package com.unionbankng.future.futureloanservice.services;
import com.unionbankng.future.futureloanservice.pojos.EmailAddress;
import com.unionbankng.future.futureloanservice.pojos.EmailBody;
import com.unionbankng.future.futureloanservice.pojos.EmailMessage;
import com.unionbankng.future.futureloanservice.util.EmailSender;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@RefreshScope
@Service
@RequiredArgsConstructor
public class EmailService {


    @Value("${email.sender}")
    private String emailSenderAddress;

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final MessageSource messageSource;
    private final EmailSender emailSender;


    public void sendEmail(EmailMessage message) {
      EmailBody emailBody = EmailBody.builder().body(messageSource.getMessage("welcome.message", new String[]{message.getBody()}, LocaleContextHolder.getLocale())
        ).sender(EmailAddress.builder().displayName("Kula Team").email(emailSenderAddress).build()).subject(message.getSubject())
                .recipients(Arrays.asList(EmailAddress.builder().email(message.getRecipient()).displayName(message.getRecipient()).build())).build();

       emailSender.sendEmail(emailBody);
    }
}
