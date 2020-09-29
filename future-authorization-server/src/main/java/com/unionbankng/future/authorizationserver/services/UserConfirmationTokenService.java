package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.enums.RecipientType;
import com.unionbankng.future.authorizationserver.pojos.EmailAddress;
import com.unionbankng.future.authorizationserver.pojos.EmailBody;
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



    @Value("${email.sender}")
    private String emailSenderAddress;
    @Value("${confirmation.token.minute.expiry}")
    private int tokenExpiryInMinute;
    @Value("${confirmation.token.url}")
    private String confirmationTokenURL;

    public void sendConfirmationToken(User user){

        logger.debug("generating token for {}",user.toString());
        String token = UUID.randomUUID().toString();
        memcachedHelperService.save(token,user.getEmail(),tokenExpiryInMinute);

        String generatedURL = String.format("%s?token=%s",confirmationTokenURL,token);
        logger.info("Sending confirmation to {}",user.toString());
        EmailBody emailBody = EmailBody.builder().body(messageSource.getMessage("welcome.message", new String[]{generatedURL,Integer.toString(tokenExpiryInMinute)}, LocaleContextHolder.getLocale())
        ).sender(EmailAddress.builder().displayName("SideKick Team").email(emailSenderAddress).build()).subject("Registration Confirmation")
                .recipients(Arrays.asList(EmailAddress.builder().recipientType(RecipientType.TO).email(user.getEmail()).displayName(user.toString()).build())).build();

        emailSender.sendEmail(emailBody);
    }

    public Boolean confirmUserAccountByToken(String token){

        String userEmail = memcachedHelperService.getValueByKey(token);

        if(userEmail == null)
            return false;

        User user  = userService.findByEmail(userEmail).orElse(null);

        if (user == null)
            return false;

        user.setIsEnabled(true);
        userService.save(user);

        return true;
    }


}
