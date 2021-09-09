
package com.unionbankng.future.authorizationserver.services;
import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.JwtUserDetail;
import com.unionbankng.future.authorizationserver.pojos.SMS;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import com.unionbankng.future.authorizationserver.utils.App;
import com.unionbankng.future.authorizationserver.utils.CryptoService;
import com.unionbankng.future.authorizationserver.utils.JWTUserDetailsExtractor;
import com.unionbankng.future.authorizationserver.utils.SMSSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.util.UUID;

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
    private final SMSSender smsSender;
    private final App app;


    public APIResponse createPin(Principal principal, String pin) {
        System.out.println("###########Creating PIN");
        System.out.println("Key:" + encryptionKey);
        System.out.println("Pin:" + pin);
        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        User user = userRepository.findByUuid(currentUser.getUserUUID()).orElse(null);
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

    public APIResponse verifyPin(Principal principal, String pin) {
        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        User user = userRepository.findByUuid(currentUser.getUserUUID()).orElse(null);
        if (user != null) {
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
        } else {
            return new APIResponse<>("Unable to fetch authentication details", false, null);
        }
    }


    public APIResponse generateOTP(Principal principal) {
        try {

            JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
            User user = userRepository.findByUuid(currentUser.getUserUUID()).orElse(null);
            if (user != null) {
                String otp = this.app.generateOTP().toString();
                memcachedHelperService.save(user.getUuid(), otp, tokenExpiryInMinute * 60);

                String mobileNumber = user.getPhoneNumber();
                if (mobileNumber.startsWith("0"))
                    mobileNumber = mobileNumber.replaceFirst("0", "+234");

                System.out.println("Sending OTP.....");

                SMS sms = new SMS();
                sms.setMessage("Your OTP is " + otp);
                sms.setRecipient(user.getPhoneNumber());
                app.print("Request:");
                app.print(sms);
                smsSender.sendSMS(sms);
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

    public APIResponse verifyOTP(Principal principal, String otp) {

        try {
            JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
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
}
