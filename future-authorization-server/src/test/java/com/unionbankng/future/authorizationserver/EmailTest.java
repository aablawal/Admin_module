package com.unionbankng.future.authorizationserver;

import com.unionbankng.future.authorizationserver.enums.RecipientType;
import com.unionbankng.future.authorizationserver.pojos.EmailAddress;
import com.unionbankng.future.authorizationserver.pojos.EmailBody;
import com.unionbankng.future.authorizationserver.utils.EmailSender;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class EmailTest extends AbstractTest{

    @Autowired
    EmailSender emailSender;

    @Test
    public void sendEmail() throws Exception {

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
