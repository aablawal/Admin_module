package com.unionbankng.future.futuremessagingservice.jmslisteners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.futuremessagingservice.interfaces.EmailProvider;
import com.unionbankng.future.futuremessagingservice.pojos.EmailBody;
import com.unionbankng.future.futuremessagingservice.smsandemailproviders.UnionEmailProvider;
import com.unionbankng.future.futuremessagingservice.util.App;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.TemplateEngine;

import java.util.Calendar;

@Component
@RequiredArgsConstructor
public class EmailListener {

    private static final String QUEUE_NAME = "kulaemailqueue";
    private final Logger logger = LoggerFactory.getLogger(EmailListener.class);
    private final TemplateEngine templateEngine;
    private final ObjectMapper mapper;
    private final App app;


    @JmsListener(destination = QUEUE_NAME, containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(String json) throws JsonProcessingException {

        EmailBody emailBody = mapper.readValue(json, EmailBody.class);
        app.print(emailBody);
        logger.info("Received message: {}", emailBody.getSubject());
        EmailProvider emailProvider = new UnionEmailProvider();

        try {
            emailProvider.send(processEmailTemplate(emailBody));
            logger.info("Email sent out");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private EmailBody processEmailTemplate(EmailBody emailBody){

        Context ctx = new Context(LocaleContextHolder.getLocale());
        if (emailBody.getRecipients().size() == 1) {
            ctx.setVariable("name", "Dear " + emailBody.getRecipients().get(0).getDisplayName().split(" ")[1] + ",");
            ctx.setVariable("footname", emailBody.getRecipients().get(0).getDisplayName());
            ctx.setVariable("year", Calendar.getInstance().get(Calendar.YEAR));
        } else {
            ctx.setVariable("name", emailBody.getSubject());
        }

        ctx.setVariable("body", emailBody.getBody());

        final String htmlContent = templateEngine.process("mail/html/system-email.html", ctx);
        emailBody.setBody(htmlContent);
        logger.info("Arranged email template ");

        return emailBody;
    }

}
