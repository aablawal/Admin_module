package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.enums.AuthProvider;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.RegistrationRequest;
import com.unionbankng.future.authorizationserver.pojos.TokenConfirm;
import com.unionbankng.future.authorizationserver.services.RegistrationService;
import com.unionbankng.future.authorizationserver.services.UserConfirmationTokenService;
import com.unionbankng.future.authorizationserver.services.UserService;
import com.unionbankng.future.authorizationserver.utils.App;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "api")
@RequiredArgsConstructor
public class RegistrationController {

    private final MessageSource messageSource;
    private final UserService userService;
    private final UserConfirmationTokenService userConfirmationTokenService;
    private final RegistrationService registrationService;
    private final App app;


    @PostMapping("/v1/registration/register")
    public ResponseEntity<APIResponse> register(@Valid @RequestBody RegistrationRequest request){

        if (userService.existsByUsername(request.getUsername()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new APIResponse(messageSource.getMessage("username.exist", null, LocaleContextHolder.getLocale()),false,null));


        app.print(request);
        if(request.getAuthProvider().equals(AuthProvider.GOOGLE))
            return registrationService.googleRegistration(request);

        return registrationService.register(request);
    }

    @PostMapping("/v1/registration/confirmation")
    public ResponseEntity<APIResponse<Long>> confirmUserAccount(@NotNull  @RequestParam String confirmationToken){

        TokenConfirm tokenConfirm = userConfirmationTokenService.confirmUserAccountByToken(confirmationToken);

        if (!tokenConfirm.getSuccess())
             return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    new APIResponse<>(messageSource.getMessage("confirmation.token.expired", null, LocaleContextHolder.getLocale()),false,null));


        return ResponseEntity.ok().body(
                new APIResponse<>(messageSource.getMessage("account.confirmation.success", null, LocaleContextHolder.getLocale()),true,tokenConfirm.getUserId()));

    }

    @PostMapping("/v1/registration/resend_confirmation")
    public ResponseEntity<APIResponse> resendAccountConfirmationEmail(@NotNull  @RequestParam String userEmail){

        User user = userService.findByEmail(userEmail).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
        //send confirmation email
        userConfirmationTokenService.sendConfirmationToken(user);

        return ResponseEntity.ok().body(
                new APIResponse("Link sent successfully",true,null));

    }

    @PostMapping("/v1/email/test")
    public ResponseEntity<APIResponse> test(){

        User user = userService.findByEmail("net.rabiualiyu@gmail.com").orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
        //send confirmation email
        userConfirmationTokenService.sendConfirmationToken(user);

        app.print("Sent");

        return ResponseEntity.ok().body(
                new APIResponse("Link sent successfully",true,null));

    }
}
