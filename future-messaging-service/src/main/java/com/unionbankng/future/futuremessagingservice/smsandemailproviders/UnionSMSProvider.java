package com.unionbankng.future.futuremessagingservice.smsandemailproviders;

import com.unionbankng.future.futuremessagingservice.config.UBNConfigurationProperties;
import com.unionbankng.future.futuremessagingservice.interfaces.SMSProvider;
import com.unionbankng.future.futuremessagingservice.pojos.SMS;
import com.unionbankng.future.futuremessagingservice.pojos.UBNSmsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UnionSMSProvider implements SMSProvider {

    private final Logger logger = LoggerFactory.getLogger(UnionSMSProvider.class);

    @Override
    public void init() {

    }

    @Override
    public void send(SMS sms) throws Exception {

        Client client = UBNConfigurationProperties.ignoreSSLClient();
        WebTarget target = client.target(UBNConfigurationProperties.SEND_SMS);

        UBNSmsRequest request = new UBNSmsRequest();
        request.setAccountNo(UBNConfigurationProperties.SMS_GL);
        request.setMessage(sms.getMessage());
        request.setSource("Kula");
        request.setMobileNo(sms.getRecipient());

        logger.info("sending sms : {}", sms.getRecipient());

        target = target.queryParam("access_token", UBNConfigurationProperties.getAccountAccessToken());
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.post(Entity.entity(request, MediaType.APPLICATION_JSON));

        if(response.getStatus() != 200){
            logger.info("sending sms failed : {}", sms.getRecipient());
            throw new HttpClientErrorException(HttpStatus.resolve(response.getStatus()));
        }

        logger.info("sending sms success : {}", sms.getRecipient());
    }
}
