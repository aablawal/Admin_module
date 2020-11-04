package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.controllers.RegistrationController;
import com.unionbankng.future.authorizationserver.entities.Profile;
import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.enums.ProfileType;
import com.unionbankng.future.authorizationserver.interfaceimpl.GoogleOauthProvider;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.RegistrationRequest;
import com.unionbankng.future.authorizationserver.pojos.ThirdPartyOauthResponse;
import com.unionbankng.future.authorizationserver.security.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private final MessageSource messageSource;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserConfirmationTokenService userConfirmationTokenService;
    private final ProfileService profileService;
    private  final GoogleOauthProvider googleOauthProvider;

    private PasswordValidator passwordValidator  = PasswordValidator.
            buildValidator(false, true, true, 6, 40);

    public ResponseEntity emailRegistration(RegistrationRequest request){

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
                .uuid(generatedUuid).password(passwordEncoder.encode(request.getPassword())).username(request.getUsername())
                .authProvider(request.getAuthProvider()).build();


        user = userService.save(user);

        //create basic profile
        Profile profile = Profile.builder().profileType(ProfileType.BASIC).userId(user.getId()).build();
        profileService.save(profile);

        //send confirmation email
        userConfirmationTokenService.sendConfirmationToken(user);

        logger.info("new email registration");
        return ResponseEntity.ok().body(
                new APIResponse(messageSource.getMessage("success.registration.message", null, LocaleContextHolder.getLocale()),true, user.getId()));

    }


    public ResponseEntity googleRegistration(RegistrationRequest request){

        if(request.getThirdPartyToken() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new APIResponse("Third party token must be provided",false,null));

        // generate uuid for user
        String generatedUuid = java.util.UUID.randomUUID().toString();
        ThirdPartyOauthResponse thirdPartyOauthResponse = googleOauthProvider.authentcate(request.getThirdPartyToken());

        if (userService.existsByEmail(thirdPartyOauthResponse.getEmail()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new APIResponse(messageSource.getMessage("email.exist", null, LocaleContextHolder.getLocale()),false,null));


        User user = User.builder().firstName(thirdPartyOauthResponse.getFirstName()).lastName(thirdPartyOauthResponse.getLastName())
                .email(thirdPartyOauthResponse.getEmail()).isEnabled(Boolean.TRUE)
                .uuid(generatedUuid).img(thirdPartyOauthResponse.getImage()).username(request.getUsername()).authProvider(request.getAuthProvider()).build();;

        user = userService.save(user);

        //create basic profile
        Profile profile = Profile.builder().profileType(ProfileType.BASIC).userId(user.getId()).build();
        profileService.save(profile);

        logger.info("new google registration");
        return ResponseEntity.ok().body(
                new APIResponse(messageSource.getMessage("success.registration.message", null, LocaleContextHolder.getLocale()),true, user.getId()));

    }
}