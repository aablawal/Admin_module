package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.enums.RecipientType;
import com.unionbankng.future.authorizationserver.pojos.EmailAddress;
import com.unionbankng.future.authorizationserver.pojos.EmailBody;
import com.unionbankng.future.authorizationserver.pojos.RegistrationRequest;
import com.unionbankng.future.authorizationserver.security.PasswordValidator;
import com.unionbankng.future.authorizationserver.services.UserService;
import com.unionbankng.future.authorizationserver.utils.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping(path = "api")
public class RegistrationController {

    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private final MessageSource messageSource;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;

    @Value("${email.sender}")
    private String emailSenderAddress;

    private PasswordValidator passwordValidator;

    private RegistrationController(MessageSource messageSource, UserService userService, PasswordEncoder passwordEncoder, EmailSender emailSender){
        this.messageSource = messageSource;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.emailSender = emailSender;
        passwordValidator = PasswordValidator.buildValidator(false, true, true, 6, 40);
    }


    @PostMapping("/v1/registration/register")
    public ResponseEntity register(@RequestBody RegistrationRequest request){

        if (userService.existsByEmail(request.getEmail()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(messageSource.getMessage("email.exist", null, LocaleContextHolder.getLocale()));

        if(!passwordValidator.validatePassword(request.getPassword()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(messageSource.getMessage("password.validation.error", null, LocaleContextHolder.getLocale()));

        // generate uuid for user
        String generatedUuid = java.util.UUID.randomUUID().toString();

        User user = User.builder().userType(request.getUserType()).firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).dialingCode(request.getDialingCode()).phoneNumber(request.getPhoneNumber()).isEnabled(Boolean.TRUE)
                .uuid(generatedUuid).password(passwordEncoder.encode(request.getPassword())).build();


        userService.save(user);

        //send welcome email
        EmailBody emailBody = EmailBody.builder().body(messageSource.getMessage("welcome.message", null, LocaleContextHolder.getLocale())
        ).sender(EmailAddress.builder().displayName("SideKick Team").email(emailSenderAddress).build()).subject("Registration Confirmation")
                .recipients(Arrays.asList(EmailAddress.builder().recipientType(RecipientType.TO).email(request.getEmail()).displayName(user.toString()).build())).build();

        emailSender.sendEmail(emailBody);

        return ResponseEntity.ok().body(messageSource.getMessage("success.registration.message", null, LocaleContextHolder.getLocale()));


    }
}
