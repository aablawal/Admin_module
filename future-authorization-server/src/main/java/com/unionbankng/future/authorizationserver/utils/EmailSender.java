package com.unionbankng.future.authorizationserver.utils;
import com.unionbankng.future.authorizationserver.enums.RecipientType;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.EmailAddress;
import com.unionbankng.future.authorizationserver.pojos.EmailBody;
import com.unionbankng.future.authorizationserver.services.UBNEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private final UBNEmailService ubnEmailService;
    private final App app;

    public void sendEmail(EmailBody emailBody) {
        try {
            APIResponse response = ubnEmailService.sendEmail(emailBody);
            app.print(response);
        } catch (Exception ex) {
            System.out.println("Unable to send Email");
            ex.printStackTrace();
        }
    }

    public void sendEmail(String recipientEmail, String userName, String subject, String body, String emailSenderAddress) {
        EmailBody emailBody = EmailBody.builder().body(body)
                .sender(EmailAddress.builder().displayName("Kula Team").email(emailSenderAddress).build()).subject(subject)
                .recipients(Arrays.asList(EmailAddress.builder().recipientType(RecipientType.TO).
                        email(recipientEmail).displayName(userName).build())).build();

        sendEmail(emailBody);
    }
}
