package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.Profile;
import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.enums.ProfileType;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.RegistrationRequest;
import com.unionbankng.future.authorizationserver.security.PasswordValidator;
import com.unionbankng.future.authorizationserver.services.ProfileService;
import com.unionbankng.future.authorizationserver.services.UserConfirmationTokenService;
import com.unionbankng.future.authorizationserver.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "api")
public class RegistrationController {

    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private final MessageSource messageSource;
    private final UserService userService;
    private final ProfileService profileService;
    private final PasswordEncoder passwordEncoder;
    private final UserConfirmationTokenService userConfirmationTokenService;

    private PasswordValidator passwordValidator;

    private RegistrationController(MessageSource messageSource, UserService userService, PasswordEncoder passwordEncoder
            ,UserConfirmationTokenService userConfirmationTokenService,ProfileService profileService){
        this.messageSource = messageSource;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.profileService = profileService;
        this.userConfirmationTokenService = userConfirmationTokenService;
        passwordValidator = PasswordValidator.buildValidator(false, true, true, 6, 40);
    }


    @PostMapping("/v1/registration/register")
    public ResponseEntity<APIResponse> register(@Valid @RequestBody RegistrationRequest request){

        if (userService.existsByUsername(request.getUsername()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new APIResponse(messageSource.getMessage("username.exist", null, LocaleContextHolder.getLocale()),false,null));

        if (userService.existsByEmail(request.getEmail()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new APIResponse(messageSource.getMessage("email.exist", null, LocaleContextHolder.getLocale()),false,null));

        if(!passwordValidator.validatePassword(request.getPassword()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    new APIResponse(messageSource.getMessage("password.validation.error", null, LocaleContextHolder.getLocale()),false,null));

        // generate uuid for user
        String generatedUuid = java.util.UUID.randomUUID().toString();

        User user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber()).dialingCode(request.getDialingCode())
                .email(request.getEmail()).dialingCode(request.getDialingCode()).phoneNumber(request.getPhoneNumber()).isEnabled(Boolean.FALSE)
                .uuid(generatedUuid).password(passwordEncoder.encode(request.getPassword())).username(request.getUsername()).build();


        user = userService.save(user);

        //create basic profile
        Profile profile = Profile.builder().profileType(ProfileType.BASIC).userId(user.getId()).build();
        profileService.save(profile);

        //send confirmation email
        userConfirmationTokenService.sendConfirmationToken(user);

        logger.info("new registration");
        return ResponseEntity.ok().body(
                new APIResponse(messageSource.getMessage("success.registration.message", null, LocaleContextHolder.getLocale()),true, null));


    }

    @PostMapping("/v1/registration/confirmation")
    public ResponseEntity<APIResponse> confirmUserAccount(@NotNull  @RequestParam String confirmationToken){

        if (!userConfirmationTokenService.confirmUserAccountByToken(confirmationToken))
             return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    new APIResponse(messageSource.getMessage("confirmation.token.expired", null, LocaleContextHolder.getLocale()),false,null));


        return ResponseEntity.ok().body(
                new APIResponse(messageSource.getMessage("account.confirmation.success", null, LocaleContextHolder.getLocale()),true,null));

    }

    @PostMapping("/v1/registration/resend_confirmation")
    public ResponseEntity<APIResponse> resendAccountConfirmationEmail(@NotNull  @RequestParam String userEmail){

        User user = userService.findByEmail(userEmail).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
        //send confirmation email
        userConfirmationTokenService.sendConfirmationToken(user);

        return ResponseEntity.ok().body(
                new APIResponse("Link sent successfully",true,null));

    }
}
