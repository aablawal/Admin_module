package com.unionbankng.future.authorizationserver.services;

import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.pojos.PersonalInfoUpdateRequest;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    @ReadThroughSingleCache(namespace = "user", expiration = 0)
    public Optional<User> findById(@ParameterValueKeyProvider Long id) {
        return userRepository.findById(id);
    }

    @ReadThroughSingleCache(namespace = "user_uuid", expiration = 0)
    public Optional<User> findByUuid(@ParameterValueKeyProvider String uuId) {
        return userRepository.findByUuid(uuId);
    }

    @ReadThroughSingleCache(namespace = "user_email", expiration = 0)
    public Optional<User> findByEmail(@ParameterValueKeyProvider String email) {
        return userRepository.findByEmail(email);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Boolean existsByEmail(String email){
        return  userRepository.existsByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @InvalidateSingleCache(namespace = "user")
    public void deleteById(@ParameterValueKeyProvider Long userId) {
         userRepository.deleteById(userId);
    }

    public Optional<User> findByEmailOrUsername(@ParameterValueKeyProvider String email, @ParameterValueKeyProvider String username){
        return userRepository.findByEmailOrUsername(email,username);
    }

    @InvalidateSingleCache(namespace = "user")
    public User updateProfileImage(MultipartFile image , @ParameterValueKeyProvider Long userId) throws IOException {

        User user = userRepository.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
        if(user.getImg() != null)
            fileStorageService.deleteFileFromStorage(user.getImg(), BlobType.IMAGE);

        String source = fileStorageService.storeFile(image,userId,BlobType.IMAGE);
        user.setImg(source);
        return userRepository.save(user);
    }


    @InvalidateSingleCache(namespace = "user")
    public User updatePersonalInfo(@ParameterValueKeyProvider Long userId, PersonalInfoUpdateRequest request) throws IOException {

        User user = userRepository.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));

        if(request.getLastName() != null)
            user.setLastName(request.getLastName());
        if(request.getFirstName() != null)
            user.setFirstName(request.getFirstName());
        if(request.getStateOfResidence() != null)
            user.setStateOfResidence(request.getStateOfResidence());
        if(request.getAddress() != null)
            user.setAddress(request.getAddress());
        if(request.getCountry() != null)
            user.setCountry(request.getCountry());
        if(request.getDateOfBirth() != null)
            user.setDateOfBirth(request.getDateOfBirth());
        if(request.getDialingCode() != null)
            user.setDialingCode(request.getDialingCode());
        if(request.getPhoneNumber() != null)
            user.setPhoneNumber(request.getPhoneNumber());

        return userRepository.save(user);
    }


    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }
}
