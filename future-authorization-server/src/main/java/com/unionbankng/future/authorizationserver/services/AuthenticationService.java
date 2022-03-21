
package com.unionbankng.future.authorizationserver.services;
import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.enums.RecipientType;
import com.unionbankng.future.authorizationserver.pojos.*;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import com.unionbankng.future.authorizationserver.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${kula.encryption.key}")
    private String encryptionKey;
    @Value("${confirmation.token.minute.expiry}")
    private int tokenExpiryInMinute;
    private final UserRepository userRepository;
    private final CryptoService cryptoService;
    private final MemcachedHelperService memcachedHelperService;
    private final MessageSource messageSource;
    private final EmailSender emailSender;
    private final SMSSender smsSender;
    @Value("${forgot.pin.url}")
    private String forgotPinURL;
    @Value("${email.sender}")
    private String emailSenderAddress;
    private final App app;


    public APIResponse createPin(OAuth2Authentication authentication, String pin) {
        System.out.println("###########Creating PIN");
        System.out.println("Key:" + encryptionKey);
        System.out.println("Pin:" + pin);
        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
        app.print(currentUser);
        User user = userRepository.findByUuid(currentUser.getUserUUID()).orElse(null);
        app.print(user);
        if (user != null) {
            if (user.getPin() == null) {
                String encrypted = cryptoService.encrypt(pin, encryptionKey);
                if (encrypted != null) {
                    user.setPin(encrypted);
                    return new APIResponse<>("Pin Added Successfully", true, userRepository.save(user));
                } else {
                    return new APIResponse<>("Unable to Create your Transaction Pin", false, null);
                }
            } else {
                return new APIResponse<>("You Already Added a Transaction Pin to your Account", false, null);
            }
        } else {
            return new APIResponse<>("Unable to fetch authentication details", false, null);
        }
    }

    public APIResponse verifyPin(OAuth2Authentication authentication, String pin) {
        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
        User user = userRepository.findByUuid(currentUser.getUserUUID()).orElse(null);
        app.print(user);
        if (user != null) {
            if(user.getPin()!=null) {
                String existingPin = cryptoService.decrypt(user.getPin(), encryptionKey);
                String providedPin = pin;
                System.out.println("##########Pin Verification started");
                System.out.println("Existing:" + existingPin);
                System.out.println("Provided:" + pin);
                if (existingPin.equals(providedPin)) {
                    return new APIResponse<>("Verification Successful", true, user);
                } else {
                    return new APIResponse<>("Invalid Pin", false, null);
                }
            }else{
                return new APIResponse<>("No Transaction PIN set to your Account", false, null);
            }
        } else {
            return new APIResponse<>("Unable to fetch authentication details", false, null);
        }
    }


    public APIResponse generateOTP(OAuth2Authentication authentication) {
        try {
            app.print("############GENERATING OTP");
            JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
            User user = userRepository.findByUuid(currentUser.getUserUUID()).orElse(null);
            app.print(user);
            if (user != null) {
                String otp = this.app.generateOTP().toString();
                memcachedHelperService.save(user.getUuid(), otp, tokenExpiryInMinute * 60);


                String mobileNumber = user.getPhoneNumber();
                if (mobileNumber.startsWith("0"))
                    mobileNumber = mobileNumber.replaceFirst("0", "234");

                app.print(mobileNumber);
                app.print("Sending OTP.....");

                SMS sms = new SMS();
                sms.setMessage("Your OTP is " + otp);
                sms.setRecipient(mobileNumber);
                app.print("Request:");
                app.print(sms);
                smsSender.sendSMS(sms);
                app.print("OTP sent successfully");
                return new APIResponse<>("OTP Sent Successfully", true, mobileNumber);

            } else {
                return new APIResponse<>("Unable to fetch authentication details", false, null);
            }
        } catch (Exception ex) {
            System.out.println("Unable to send OTP");
            ex.printStackTrace();
            return new APIResponse<>(ex.getMessage(), false, null);

        }
    }

    public APIResponse verifyOTP(OAuth2Authentication authentication, String otp) {

        try {
            JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
            User user = userRepository.findByUuid(currentUser.getUserUUID()).orElse(null);
            if (user != null) {
                System.out.println("Verifying OTP....");
                String memOTP = memcachedHelperService.getValueByKey(user.getUuid());
                if (memOTP.equals(otp)) {
                    return new APIResponse<>("Verification Successful", true, null);
                } else {
                    return new APIResponse<>("Invalid OTP", false, null);
                }
            } else {
                return new APIResponse<>("Unable to fetch authentication details", false, null);
            }

        } catch (Exception ex) {
            System.out.println("Unable to verify OTP");
            ex.printStackTrace();
            return new APIResponse<>(ex.getMessage(), false, null);
        }
    }

    public APIResponse<String> initiateForgotPin(String email) {

        app.print("#########Initiating forgot pin");
        app.print(email);
        Optional<User> emailOwner = userRepository.findByEmailOrUsername(email,email);

        if(emailOwner.isEmpty()){
            return new APIResponse<>("This email is not registered", false, null);
        }
        User user = emailOwner.get();

        String token = UUID.randomUUID().toString();

        app.print("#### Pin Reset");
        app.print(user);
        app.print(token);

        memcachedHelperService.save(token,user.getEmail(),tokenExpiryInMinute * 60);
        String generatedURL = String.format("%s?token=%s",forgotPinURL,token);

        app.print("Reset Password Token:");
        app.print(generatedURL);

        EmailBody emailBody = EmailBody.builder().body(messageSource.getMessage("forgot.pin", new String[]{generatedURL,
                        Utility.convertMinutesToWords(tokenExpiryInMinute)}, LocaleContextHolder.getLocale())
                ).sender(EmailAddress.builder().displayName("Kula Team").email(emailSenderAddress).build()).subject("Reset Your Kula Pin")
                .recipients(Arrays.asList(EmailAddress.builder().recipientType(RecipientType.TO).
                        email(user.getEmail()).displayName(user.toString()).build())).build();

        emailSender.sendEmail(emailBody);

        APIResponse<String> response = new APIResponse<>("Pin Reset Email Sent", true, null);

        return response;
    }

    public ResponseEntity<?> resetPin(String token, String newPin) {

        app.print("Resetting user pin");
        app.print(token);

        String userEmail = memcachedHelperService.getValueByKey(token);
        app.print("Memcached Value:"+userEmail);

        if(userEmail == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new APIResponse("Token expired or not found",false,null));

        User user  = userRepository.findByEmail(userEmail).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));

        String encrypted = cryptoService.encrypt(newPin, encryptionKey);
        if (encrypted != null) {
            user.setPin(encrypted);
            memcachedHelperService.clear(token);
            return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>("Pin reset Successfully", true, userRepository.save(user)));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new APIResponse<>("Unable to Create your Transaction Pin", false, null));
        }

    }
}















