package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.entities.UserConfirmationToken;
import com.unionbankng.future.authorizationserver.enums.RecipientType;
import com.unionbankng.future.authorizationserver.pojos.EmailAddress;
import com.unionbankng.future.authorizationserver.pojos.EmailBody;
import com.unionbankng.future.authorizationserver.repositories.UserConfirmationTokenRepository;
import com.unionbankng.future.authorizationserver.utils.EmailSender;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class UserConfirmationTokenService {

    public static Integer VERIFICATION_FAILED = 0;
    public static Integer VERIFICATION_TOKEN_EXPIRED = 1;
    public static Integer VERIFICATION_TOKEN_VERIFIED = 2;

    private final Logger logger = LoggerFactory.getLogger(UserConfirmationTokenService.class);
    private final MessageSource messageSource;
    private final UserService userService;
    private final UserConfirmationTokenRepository userConfirmationTokenRepository;
    private final EmailSender emailSender;



    @Value("${email.sender}")
    private String emailSenderAddress;
    @Value("${confirmation.token.minute.expiry}")
    private int tokenExpiryInMinute;
    @Value("${confirmation.token.url}")
    private String confirmationTokenURL;

    public void sendConfirmationToken(User user){

        logger.debug("generating token for {}",user.toString());
        UserConfirmationToken confirmationToken = new UserConfirmationToken(user,tokenExpiryInMinute);
        userConfirmationTokenRepository.save(confirmationToken);

        String generatedURL = confirmationTokenURL+"?token="+confirmationToken.getToken();

        logger.info("Sending confirmation to {}",user.toString());
        EmailBody emailBody = EmailBody.builder().body(messageSource.getMessage("welcome.message", new String[]{generatedURL,Integer.toString(tokenExpiryInMinute)}, LocaleContextHolder.getLocale())
        ).sender(EmailAddress.builder().displayName("SideKick Team").email(emailSenderAddress).build()).subject("Registration Confirmation")
                .recipients(Arrays.asList(EmailAddress.builder().recipientType(RecipientType.TO).email(user.getEmail()).displayName(user.toString()).build())).build();

        emailSender.sendEmail(emailBody);
    }

    public Integer confirmUserAccountByToken(String token){

        UserConfirmationToken verificationToken = userConfirmationTokenRepository.findByToken(token).orElse(null);
        if (verificationToken == null)
            return VERIFICATION_FAILED;

        User user = verificationToken.getUser();

        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0)
            return VERIFICATION_TOKEN_EXPIRED;

        user.setIsEnabled(true);
        userService.save(user);

        return VERIFICATION_TOKEN_VERIFIED;
    }


}
