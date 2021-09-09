
package com.unionbankng.future.authorizationserver.services;
import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.JwtUserDetail;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import com.unionbankng.future.authorizationserver.utils.CryptoService;
import com.unionbankng.future.authorizationserver.utils.JWTUserDetailsExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class PinService {

//    @Value("${kula.encryption.key}")
    private String encryptionKey="Gh79j96-6762-493c-837949";
    private final UserRepository userRepository;
    private final CryptoService cryptoService;

    public APIResponse createPin(Principal principal, String pin) {
        System.out.println("###########Creating PIN");
        System.out.println("Key:"+encryptionKey);
        System.out.println("Pin:"+pin);
        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        User user=userRepository.findByUuid(currentUser.getUserUUID()).orElse(null);
        if(user!=null){
            if(user.getPin()==null) {
                String encrypted = cryptoService.encrypt(pin, encryptionKey);
                if (encrypted != null) {
                    user.setPin(encrypted);
                    return new APIResponse<>("Pin Added Successfully", true, userRepository.save(user));
                } else {
                    return new APIResponse<>("Unable to Create your Transaction Pin", false, null);
                }
            }else{
               return new APIResponse<>("You Already Added a Transaction Pin to your Account", false, null);
            }
        }else{
            return new APIResponse<>("Unable to fetch authentication details", false, null);
        }
    }

    public APIResponse verifyPin(Principal principal, String pin){
        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        User user=userRepository.findByUuid(currentUser.getUserUUID()).orElse(null);
        if(user!=null){
            String existingPin=cryptoService.decrypt(user.getPin(),encryptionKey);
            String providedPin=pin;
            System.out.println("##########Pin Verification started");
            System.out.println("Existing:"+existingPin);
            System.out.println("Provided:"+pin);
            if(existingPin.equals(providedPin)) {
                return new APIResponse<>("Verification Successful", true, user);
            }else{
                return new APIResponse<>("Invalid Pin", false, null);
            }
        }else{
            return new APIResponse<>("Unable to fetch authentication details", false, null);
        }
    }

}
