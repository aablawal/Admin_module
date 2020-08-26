package com.unionbankng.future.futuremessagingservice;

import com.unionbankng.future.futuremessagingservice.interfaces.SMSProvider;
import com.unionbankng.future.futuremessagingservice.pojo.SMS;
import com.unionbankng.future.futuremessagingservice.smsproviders.UnionSMSProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class UnionSMSProviderTests {

    @Test
    void sendSMS() throws Exception {
        SMSProvider UBNSMSProvider = new UnionSMSProvider();
        SMS sms = new SMS();
        sms.setMessage("Hello World");
        sms.setRecipient("08058905125");

        UBNSMSProvider.send(sms);

    }
}
