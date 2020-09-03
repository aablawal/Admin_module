package com.unionbankng.future.authorizationserver;

import com.unionbankng.future.authorizationserver.enums.RecipientType;
import com.unionbankng.future.authorizationserver.pojos.EmailAddress;
import com.unionbankng.future.authorizationserver.pojos.EmailBody;
import com.unionbankng.future.authorizationserver.utils.EmailSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
public class EmailTest {

    @Autowired
    EmailSender emailSender;

    @Test
    void sendEmail() throws Exception {

        EmailBody emailBody = EmailBody.builder().build();
        emailBody.setBody("Hello world");
        emailBody.setFooter("Test Footer");
        emailBody.setSubject("Hello Test");

        List<EmailAddress> addresses = new ArrayList<>();
        EmailAddress recipientAddress = EmailAddress.builder().recipientType(RecipientType.BCC).
                displayName("Okeme Christian").email("chokeme@unionbankng.com").build();
        addresses.add(recipientAddress);
        EmailAddress senderAddress = EmailAddress.builder().recipientType(RecipientType.BCC).
                displayName("Sheriff Saliu").email("sosaliu@unionbankng.com").build();

        emailBody.setRecipients(addresses);
        emailBody.setSender(senderAddress);

        emailSender.sendEmail(emailBody);

    }
}
