package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.enums.RecipientType;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.ChangePasswordRequest;
import com.unionbankng.future.authorizationserver.pojos.EmailAddress;
import com.unionbankng.future.authorizationserver.pojos.EmailBody;
import com.unionbankng.future.authorizationserver.security.PasswordValidator;
import com.unionbankng.future.authorizationserver.utils.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final UserService userService;
    private final MemcachedHelperService memcachedHelperService;
    private final MessageSource messageSource;
    private final EmailSender emailSender;
    private final PasswordEncoder encoder;
    private PasswordValidator passwordValidator = PasswordValidator.buildValidator(false, true, true, 6, 40);


    @Value("${email.sender}")
    private String emailSenderAddress;
    @Value("${forgot.token.seconds.expiry}")
    private int tokenExpiryInMinute;
    @Value("${forgot.password.url}")
    private String forgotPasswordURL;

    public void initiateForgotPassword(String identifier){

        User user = userService.findByEmailOrUsername(identifier,identifier).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));

        String token = UUID.randomUUID().toString();
        memcachedHelperService.save(token,user.getEmail(),tokenExpiryInMinute);

        String generatedURL = String.format("%s?token=%s",forgotPasswordURL,token);

        EmailBody emailBody = EmailBody.builder().body(messageSource.getMessage("forgot.password", new String[]{generatedURL,String.format("%s $s",tokenExpiryInMinute,"minutes")}, LocaleContextHolder.getLocale())
        ).sender(EmailAddress.builder().displayName("SideKick Team").email(emailSenderAddress).build()).subject("Reset Your Sidekick Password")
                .recipients(Arrays.asList(EmailAddress.builder().recipientType(RecipientType.TO).email(user.getEmail()).displayName(user.toString()).build())).build();

        emailSender.sendEmail(emailBody);
    }

    public Boolean confirmForgotPasswordToken(String token){

        String userEmail = memcachedHelperService.getValueByKey(token);
        return userEmail != null;

    }

    public ResponseEntity resetPassword(String token, String password){

        String userEmail = memcachedHelperService.getValueByKey(token);
        if(userEmail == null)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    new APIResponse("Token expired or not found",false,null));


        if(!passwordValidator.validatePassword(password))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    new APIResponse(messageSource.getMessage("password.validation.error",
                            null, LocaleContextHolder.getLocale()),false,null));

        User user  = userService.findByEmail(userEmail).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));

        user.setPassword(encoder.encode(password));

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Password reset successful",true,null));


    }

    public ResponseEntity<APIResponse> changePassword(ChangePasswordRequest request){

        if(!passwordValidator.validatePassword(request.getPassword()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    new APIResponse(messageSource.getMessage("password.validation.error",
                            null, LocaleContextHolder.getLocale()),false,null));

        if (request.getPassword().equals(request.getOldPassword()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    new APIResponse("New password and old password can't be the same",false,null));

        User user = userService.findById(request.getUserId()).orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));

        if (!encoder.matches(request.getOldPassword(), user.getPassword()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    new APIResponse("You have entered an incorrect old password",false,null));


        user.setPassword(encoder.encode(request.getPassword()));
        userService.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request Successful",true,null));
    }
}