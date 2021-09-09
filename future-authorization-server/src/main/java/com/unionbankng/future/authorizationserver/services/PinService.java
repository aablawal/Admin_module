
package com.unionbankng.future.authorizationserver.services;
import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.JwtUserDetail;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import com.unionbankng.future.authorizationserver.utils.JWTUserDetailsExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class PinService {

    @Value("${kula.encryption.key}")
    private String encryptionKey="Rabs@1994";
    private final UserRepository userRepository;
    private final EncryptionService encryptionService;

    public APIResponse createPin(Principal principal, String pin) {
        System.out.println("###########Creating PIN");
        System.out.println("Key:"+encryptionKey);
        System.out.println("Pin:"+pin);
        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        User user=userRepository.findByUuid(currentUser.getUserUUID()).orElse(null);
        if(user!=null){
            user.setPin((encryptionService.encrypt(pin,encryptionKey)));
            return new APIResponse<>("Pin Added Successfully", true,   userRepository.save(user));
        }else{
            return new APIResponse<>("Account not found", false, null);
        }
    }

    public APIResponse verifyPin(Principal principal, String pin){
        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        User user=userRepository.findByUuid(currentUser.getUserUUID()).orElse(null);
        if(user!=null){
            String existingPin=encryptionService.decrypt(user.getPin(),encryptionKey);
            String providedPin=pin;
            System.out.println("##########Pin Verification started");
            System.out.println("Existing:"+existingPin);
            System.out.println("Provided:"+pin);
            if(existingPin.equals(pin)) {
                return new APIResponse<>("Pin Verified Successfully", true, user);
            }else{
                return new APIResponse<>("Invalid Pin", false, null);
            }
        }else{
            return new APIResponse<>("Account not found", false, null);
        }
    }

}
