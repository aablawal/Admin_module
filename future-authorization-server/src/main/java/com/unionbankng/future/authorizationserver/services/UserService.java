package com.unionbankng.future.authorizationserver.services;

import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.unionbankng.future.authorizationserver.entities.Profile;
import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.pojos.PersonalInfoUpdateRequest;
import com.unionbankng.future.authorizationserver.repositories.ProfileRepository;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import com.unionbankng.future.authorizationserver.utils.App;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final FileStorageService fileStorageService;
    private final App app;

    private final ProfileStepService profileStepService;


//    @Cacheable(value = "user", key = "#id")
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<List<User>> findUsersBySearch(String question) {
        return userRepository.findUsersBySearch(question);
    }
    public Page<User> findUsers(Pageable  pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<User> findByUuid(String uuId) {
        return userRepository.findByUuid(uuId);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @CacheEvict(value = "user", key = "#userId")
    public void deleteById(@ParameterValueKeyProvider Long userId) {
        userRepository.deleteById(userId);
    }

    public Optional<User> findByEmailOrUsername(@ParameterValueKeyProvider String email, @ParameterValueKeyProvider String username) {
        return userRepository.findByEmailOrUsername(email, username);
    }

    @CachePut(value = "user", key = "#userId")
    public User updateProfileImage(MultipartFile image, @ParameterValueKeyProvider Long userId) throws IOException {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
        if (user.getImg() != null)
            fileStorageService.deleteFileFromStorage(user.getImg(), BlobType.IMAGE);

        String source = fileStorageService.storeFile(image, userId, BlobType.IMAGE);
        user.setImg(source);
        return userRepository.save(user);
    }


    @CachePut(value = "user", key = "#userId")
    public User updatePersonalInfo(@ParameterValueKeyProvider Long userId, PersonalInfoUpdateRequest request) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getStateOfResidence() != null)
            user.setStateOfResidence(request.getStateOfResidence());
        if (request.getAddress() != null)
            user.setUserAddress(request.getAddress());
        if (request.getCountry() != null)
            user.setCountry(request.getCountry());
        if (request.getDateOfBirth() != null)
            user.setDateOfBirth(request.getDateOfBirth());
        if (request.getDialingCode() != null)
            user.setDialingCode(request.getDialingCode());
        if (request.getPhoneNumber() != null)
            user.setPhoneNumber(request.getPhoneNumber());
        return userRepository.save(user);
    }

    @CachePut(value = "user", key = "#userId")
    public User updateProfile(@ParameterValueKeyProvider Long userId, MultipartFile coverImg, MultipartFile img, PersonalInfoUpdateRequest request) throws IOException {
        app.print("Updating user profile");
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
        Profile profile = profileRepository.findByUserId(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Profile not found"));

        if (img != null) {
            app.print("Updating user profile image");
            if (user.getImg() != null){
                app.print("Deleting old image");
                fileStorageService.deleteFileFromStorage(user.getImg(), BlobType.IMAGE);
                app.print("Deleted old image");
                app.print("Decrementing percentage profile complete");
                profileStepService.decrementPercentageComplete(profile.getId(),15);
                app.print("Percentage profile complete decreased");
            }
            String source = fileStorageService.storeFile(img, userId, BlobType.IMAGE);
            user.setImg(source);
            profile.setProfilePhoto(source);
            app.print("incrementing percentage profile complete");
            profileStepService.incrementPercentageComplete(profile.getId(),15);
            app.print("Percentage profile complete increased");
            app.print(source);
        }

        if (coverImg != null) {
            app.print("Updating user cover image");
            if (user.getCoverImg() != null){
                app.print("Deleting old cover image");
                fileStorageService.deleteFileFromStorage(user.getCoverImg(), BlobType.IMAGE);
                app.print("Deleted old cover image");
                app.print("Decrementing percentage profile complete");
                profileStepService.decrementPercentageComplete(profile.getId(),5);
                app.print("Percentage profile complete decreased");
            }
            String source = fileStorageService.storeFile(coverImg, userId, BlobType.IMAGE);
            user.setCoverImg(source);
            profile.setCoverPhoto(source);
            app.print("incrementing percentage profile complete");
            profileStepService.incrementPercentageComplete(profile.getId(),5);
            app.print("Percentage profile complete increased");
            app.print(source);
        }

        profileRepository.save(profile);

//        Boolean isKeycloakPropertyChanged = !user.getLastName().equals(request.getLastName()) ||
//                !user.getFirstName().equals(request.getFirstName());


        if(request != null){
            app.print("Updating user profile information");
            user.setLastName(!request.getLastName().isBlank() ? request.getLastName() : user.getLastName());
            user.setFirstName(!request.getFirstName().isBlank() ? request.getFirstName() : user.getFirstName());
            user.setCountry(!request.getCountry().isBlank() ? request.getCountry() : user.getCountry());
            user.setStateOfResidence(request.getStateOfResidence());
            user.setUserAddress(request.getAddress());
            user.setDateOfBirth(request.getDateOfBirth());
            user.setCity(request.getCity());
            user.setZipCode(request.getZipCode());
        }


        return userRepository.save(user);
    }
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

}
