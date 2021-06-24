package com.unionbankng.future.futuremessagingservice.jmslisteners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.futuremessagingservice.interfaces.SMSProvider;
import com.unionbankng.future.futuremessagingservice.pojos.SMS;
import com.unionbankng.future.futuremessagingservice.smsandemailproviders.UnionSMSProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SMSListener {

    private static final String QUEUE_NAME = "smsqueue";

    private final Logger logger = LoggerFactory.getLogger(SMSListener.class);
    private final ObjectMapper mapper;

    @JmsListener(destination = QUEUE_NAME, containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(String json) throws JsonProcessingException {
        SMS sms = mapper.readValue(json, SMS.class);
        logger.info("Received message: {}", sms.getRecipient());
        SMSProvider UBNSMSProvider = new UnionSMSProvider();
        try {
            UBNSMSProvider.send(sms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
