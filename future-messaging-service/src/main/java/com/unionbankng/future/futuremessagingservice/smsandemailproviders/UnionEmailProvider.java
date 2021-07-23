package com.unionbankng.future.futuremessagingservice.smsandemailproviders;

import com.unionbankng.future.futuremessagingservice.config.UBNConfigurationProperties;
import com.unionbankng.future.futuremessagingservice.interfaces.EmailProvider;
import com.unionbankng.future.futuremessagingservice.pojos.EmailBody;
import com.unionbankng.future.futuremessagingservice.util.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UnionEmailProvider implements EmailProvider {

    private final Logger logger = LoggerFactory.getLogger(EmailProvider.class);

    @Override
    public void init() {

    }

    @Override
    public void send(EmailBody emailBody) throws Exception {

        Client client = UBNConfigurationProperties.ignoreSSLClient();
        WebTarget target = client.target(UBNConfigurationProperties.EMAIL_URL);


        logger.info("URL:"+UBNConfigurationProperties.EMAIL_URL);
        logger.info("Token:"+ UBNConfigurationProperties.getAccountAccessToken());
        logger.info("sending email : {}", emailBody.getSubject());

        target = target.queryParam("access_token", UBNConfigurationProperties.getAccountAccessToken());
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.post(Entity.entity(emailBody, MediaType.APPLICATION_JSON));
        if(response.getStatus() != 200){
            logger.info("sending email failed : {}", emailBody.getSubject());
            throw new HttpClientErrorException(HttpStatus.resolve(response.getStatus()));
        }

        logger.info("sending email success : {}", emailBody.getSubject());
    }
}
