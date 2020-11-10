package com.unionbankng.future.futuremessagingservice;

import com.unionbankng.future.futuremessagingservice.enums.RecipientType;
import com.unionbankng.future.futuremessagingservice.interfaces.EmailProvider;
import com.unionbankng.future.futuremessagingservice.pojo.EmailAddress;
import com.unionbankng.future.futuremessagingservice.pojo.EmailBody;
import com.unionbankng.future.futuremessagingservice.smsandemailproviders.UnionEmailProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
public class UnionEmailProviderTests {

    @Test
    void sendEmail() throws Exception {

        EmailBody emailBody = new EmailBody();
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

        EmailProvider emailProvider = new UnionEmailProvider();
        emailProvider.send(emailBody);

    }
}
