package com.unionbankng.future.authorizationserver.utils;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.EmailBody;
import com.unionbankng.future.authorizationserver.services.UBNEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
}
