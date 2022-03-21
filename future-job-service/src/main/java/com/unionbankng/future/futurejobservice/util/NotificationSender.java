package com.unionbankng.future.futurejobservice.util;
import com.unionbankng.future.futurejobservice.enums.RecipientType;
import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import com.unionbankng.future.futurejobservice.pojos.EmailAddress;
import com.unionbankng.future.futurejobservice.pojos.EmailBody;
import com.unionbankng.future.futurejobservice.pojos.NotificationBody;
import com.unionbankng.future.futurejobservice.services.UBNEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class NotificationSender {

    private static final String NOTIFICATION_DESTINATION = "kulanotificationqueue";
    private final JmsTemplate jmsTemplate;
    private final UBNEmailService ubnEmailService;
    private final App app;

    public void pushNotification(NotificationBody notificationBody){
        try {
            jmsTemplate.convertAndSend(NOTIFICATION_DESTINATION, notificationBody);

            //send an email for priority notifications
            if(notificationBody.getPriority().equals("YES")){
                app.print("Sending Notification to Email.....");
                app.print(notificationBody);
                EmailBody emailBody = EmailBody.builder().body(notificationBody.getBody()
                        ).sender(EmailAddress.builder().displayName("Kula Team").email("hello@kula.work").build()).subject(notificationBody.getSubject())
                        .recipients(Arrays.asList(EmailAddress.builder().recipientType(RecipientType.TO).email(notificationBody.getRecipientEmail()).displayName(notificationBody.getRecipientName()).build())).build();

                 APIResponse apiResponse=ubnEmailService.sendEmail(emailBody);
                app.print("Message Queued successfully");
                app.print(apiResponse);
            }else{
                app.print("Notification not a Priority one");
                app.print(notificationBody);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
