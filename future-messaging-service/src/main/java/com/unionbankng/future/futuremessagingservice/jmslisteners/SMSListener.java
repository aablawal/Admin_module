package com.unionbankng.future.futuremessagingservice.jmslisteners;

import com.unionbankng.future.futuremessagingservice.interfaces.SMSProvider;
import com.unionbankng.future.futuremessagingservice.pojo.SMS;
import com.unionbankng.future.futuremessagingservice.smsproviders.UnionSMSProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class SMSListener {

    private static final String QUEUE_NAME = "futuresmsqueue";

    private final Logger logger = LoggerFactory.getLogger(SMSListener.class);

    @JmsListener(destination = QUEUE_NAME, containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(SMS sms) {
        logger.info("Received message: {}", sms.getRecipient());
        SMSProvider UBNSMSProvider = new UnionSMSProvider();
        try {
            UBNSMSProvider.send(sms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
