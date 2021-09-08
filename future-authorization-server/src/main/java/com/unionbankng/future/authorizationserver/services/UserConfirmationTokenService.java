package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.enums.RecipientType;
import com.unionbankng.future.authorizationserver.pojos.EmailAddress;
import com.unionbankng.future.authorizationserver.pojos.EmailBody;
import com.unionbankng.future.authorizationserver.pojos.TokenConfirm;
import com.unionbankng.future.authorizationserver.utils.EmailSender;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.UUID;

@RefreshScope
@Service
@RequiredArgsConstructor
public class UserConfirmationTokenService {

    private final Logger logger = LoggerFactory.getLogger(UserConfirmationTokenService.class);
    private final MessageSource messageSource;
    private final UserService userService;
    private final EmailSender emailSender;
    private final MemcachedHelperService memcachedHelperService;
    private final KeycloakService keycloakService;

    @Value("${email.sender}")
    private String emailSenderAddress;
    @Value("${confirmation.token.minute.expiry}")
    private int tokenExpiryInMinute;
    @Value("${confirmation.token.url}")
    private String confirmationTokenURL;

    public void sendConfirmationToken(User user) {

        logger.info("=============================");
        logger.debug("generating token for {}", user.toString());
        String token = UUID.randomUUID().toString();
        memcachedHelperService.save(token, user.getEmail(), tokenExpiryInMinute * 60);

        String generatedURL="No URL";// = String.format("%s?token=%s", confirmationTokenURL, token);
        logger.info("Sending confirmation to {}", user.toString());
        logger.info("Activation Link:"+generatedURL);

        EmailBody emailBody = EmailBody.builder().body(messageSource.getMessage("welcome.message", new String[]{generatedURL}, LocaleContextHolder.getLocale())
        ).sender(EmailAddress.builder().displayName("Kula Team").email(emailSenderAddress).build()).subject("Registration Confirmation")
                .recipients(Arrays.asList(EmailAddress.builder().recipientType(RecipientType.TO).email(user.getEmail()).displayName(user.toString()).build())).build();

        emailSender.sendEmail(emailBody);
        logger.info("Message Queued successfully");
    }


    public void sendEmailTest(String email) {
        EmailBody emailBody = EmailBody.builder().body(messageSource.getMessage("welcome.message", new String[]{"Email test from kula app"}, LocaleContextHolder.getLocale())
                ).sender(EmailAddress.builder().displayName("Kula Team").email(emailSenderAddress).build()).subject("Kula Test")
                .recipients(Arrays.asList(EmailAddress.builder().recipientType(RecipientType.TO).email(email).displayName("Kula User").build())).build();

        emailSender.sendEmail(emailBody);
        logger.info("Message Queued successfully");
    }

    public TokenConfirm confirmUserAccountByToken(String token) {

        logger.info("=============================");
        logger.info("Token: "+token);

        TokenConfirm tokenConfirm = new TokenConfirm();
        String userEmail = memcachedHelperService.getValueByKey(token);
        if (userEmail != null) {
            User user = userService.findByEmail(userEmail).orElse(null);
            if (user!= null) {
                tokenConfirm.setSuccess(false);
                keycloakService.enableKeyCloakUser(user.getUuid());
                user.setIsEnabled(true);
                userService.save(user);

                memcachedHelperService.clear(token);
                tokenConfirm.setSuccess(true);
                tokenConfirm.setUserId(user.getId());
                return tokenConfirm;
            }else{
                logger.info("User not found from the token");
                return  null;
            }
        }else{
            logger.info("User not found from the email got from the token");
            return  null;
        }
    }
}
