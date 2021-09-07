
package com.unionbankng.future.authorizationserver.services;
import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.JwtUserDetail;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import com.unionbankng.future.authorizationserver.utils.App;
import com.unionbankng.future.authorizationserver.utils.JWTUserDetailsExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class PinService {

    @Value("${kula.encryption.key}")
    private int key=777;
    private final UserRepository userRepository;
    private final App app;

    public APIResponse createPin(Principal principal, String pin) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        System.out.println("###########Creating PIN");
        System.out.println("Key:"+key);
        System.out.println("Pin:"+pin);
        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        User user=userRepository.findByUuid(currentUser.getUserUUID()).orElse(null);
        if(user!=null){
            user.setPin((app.encrypt(pin,key)));
            return new APIResponse<>("Pin Added Successfully", true,   userRepository.save(user));
        }else{
            return new APIResponse<>("Account not found", false, null);
        }
    }

    public APIResponse verifyPin(Principal principal, String pin) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        User user=userRepository.findByUuid(currentUser.getUserUUID()).orElse(null);
        if(user!=null){
            String existingPin=app.decrypt(user.getPin(),key);
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
