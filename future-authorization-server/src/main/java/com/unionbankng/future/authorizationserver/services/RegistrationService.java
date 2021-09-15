package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.controllers.RegistrationController;
import com.unionbankng.future.authorizationserver.entities.Profile;
import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.enums.ProfileType;
import com.unionbankng.future.authorizationserver.interfaceimpl.GoogleOauthProvider;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.ErrorResponse;
import com.unionbankng.future.authorizationserver.pojos.RegistrationRequest;
import com.unionbankng.future.authorizationserver.pojos.ThirdPartyOauthResponse;
import com.unionbankng.future.authorizationserver.security.PasswordValidator;
import com.unionbankng.future.authorizationserver.utils.App;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Arrays;

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
    private final RealmResource keycloakRealmResource;
    private final App app;

    private PasswordValidator passwordValidator  = PasswordValidator.
            buildValidator(false, true, true, 6, 40);

    public ResponseEntity register(RegistrationRequest request){

        app.print("####REGISTER WITH KULA FORM");
        if (userService.existsByEmail(request.getEmail()) || userService.existsByUsername(request.getUsername())) {
            User existingUser=userService.findByEmail(request.getEmail()).orElse(
                    userService.findByUsername(request.getUsername()).orElse(null)
            );
            ErrorResponse errorResponse = new ErrorResponse();
            if(existingUser.getIsEnabled()) {
                errorResponse.setCode("00");
                errorResponse.setRemark(messageSource.getMessage("account.active", null, LocaleContextHolder.getLocale()));

            }else {
                //send confirmation email
                userConfirmationTokenService.sendConfirmationToken(existingUser);
                errorResponse.setCode("01");
                errorResponse.setRemark(messageSource.getMessage("account.inactive", null, LocaleContextHolder.getLocale()));
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new APIResponse(errorResponse.getRemark(), false, errorResponse));
        }

        if(!passwordValidator.validatePassword(request.getPassword()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    new APIResponse(messageSource.getMessage("password.validation.error", null, LocaleContextHolder.getLocale()),false,null));


        Response response = createUserOnKeycloak(request);
        logger.info("Response: {} {}", response.getStatus(), response.getStatusInfo());

        if(response.getStatus() != 201) {
            if(response.getStatus()==409){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        new APIResponse(messageSource.getMessage("email.exist", null, LocaleContextHolder.getLocale()), false, null));
            }else {
                return ResponseEntity.status(response.getStatus()).body(
                        new APIResponse(response.getStatusInfo().getReasonPhrase(), false, null));
            }
        }

        // generate uuid for user
        String generatedUuid =  CreatedResponseUtil.getCreatedId(response);
        User user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber()).dialingCode(request.getDialingCode())
                .email(request.getEmail()).dialingCode(request.getDialingCode()).phoneNumber(request.getPhoneNumber()).isEnabled(Boolean.FALSE)
                .uuid(generatedUuid).password(passwordEncoder.encode(request.getPassword())).username(request.getUsername())
                .authProvider(request.getAuthProvider()).build();


        user = userService.save(user);
        app.print("Saved user");
        app.print(user);
        //create basic profile
        Profile profile = Profile.builder().profileType(ProfileType.BASIC).userId(user.getId()).build();
        profileService.save(profile);
        //send confirmation email
        userConfirmationTokenService.sendConfirmationToken(user);

        logger.info("new email registration");
        return ResponseEntity.ok().body(
                new APIResponse(messageSource.getMessage("success.registration.message", null, LocaleContextHolder.getLocale()),true, user.getId()));

    }


    public Response createUserOnKeycloak(RegistrationRequest request){

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(request.getUsername());
        userRepresentation.setFirstName(request.getFirstName());
        userRepresentation.setLastName(request.getLastName());
        userRepresentation.setEmail(request.getEmail());
        userRepresentation.setEmailVerified(false);
        userRepresentation.setEnabled(false);

        // Define password credential
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(request.getPassword());
        userRepresentation.setCredentials(Arrays.asList(passwordCred));

        // Assign realm role tester to user
        userRepresentation.setRealmRoles(Arrays.asList("user"));

        // Get realm
        UsersResource usersResource = keycloakRealmResource.users();

        app.print(userRepresentation.getClientConsents());

        return usersResource.create(userRepresentation);

    }


    public ResponseEntity googleRegistration(RegistrationRequest request){

        if(request.getThirdPartyToken() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new APIResponse("Third party token must be provided",false,null));


        app.print("##############LOGIN WITH GOOGLE");
        // generate uuid for user
        ThirdPartyOauthResponse thirdPartyOauthResponse = googleOauthProvider.authentcate(request.getThirdPartyToken());

        if (userService.existsByEmail(thirdPartyOauthResponse.getEmail()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new APIResponse(messageSource.getMessage("email.exist", null, LocaleContextHolder.getLocale()),false,null));

        Response response = createUserOnKeycloak(request);
        logger.info("Response: {} {}", response.getStatus(), response.getStatusInfo());

        if(response.getStatus() != 201)
            return ResponseEntity.status(response.getStatus()).body(
                    new APIResponse(response.getStatusInfo().getReasonPhrase(),false,null));

        // generate uuid for user
        String generatedUuid =  CreatedResponseUtil.getCreatedId(response);

        User user = User.builder().firstName(thirdPartyOauthResponse.getFirstName()).lastName(thirdPartyOauthResponse.getLastName())
                .email(thirdPartyOauthResponse.getEmail()).isEnabled(Boolean.TRUE)
                .uuid(generatedUuid).img(thirdPartyOauthResponse.getImage()).username(request.getUsername()).authProvider(request.getAuthProvider()).build();;


        user = userService.save(user);
        app.print("Saved user:");
        app.print(user);

        //create basic profile
        Profile profile = Profile.builder().profileType(ProfileType.BASIC).userId(user.getId()).build();
        profileService.save(profile);

        logger.info("new google registration");
        return ResponseEntity.ok().body(
                new APIResponse(messageSource.getMessage("success.registration.message", null, LocaleContextHolder.getLocale()),true, user.getId()));

    }
}
