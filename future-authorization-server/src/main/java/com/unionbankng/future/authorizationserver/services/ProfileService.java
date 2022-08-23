package com.unionbankng.future.authorizationserver.services;
import com.unionbankng.future.authorizationserver.entities.Profile;
import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.pojos.ProfileUpdateRequest;
import com.unionbankng.future.authorizationserver.repositories.ProfileRepository;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import com.unionbankng.future.authorizationserver.utils.App;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final ExperienceService experienceService;

    private final QualificationService qualificationService;

    private final TrainingService trainingService;

    private final SocialLinkService socialLinkService;

    private final UserService userService;

    private final FileStorageService fileStorageService;
    private final UserRepository userRepository;
    private final App app;


    @Cacheable(value = "user_profile", key="#userId")
    public Optional<Profile> findByUserId (Long userId){
        return profileRepository.findByUserId(userId);
    }


    public Profile save (Profile profile){
        return profileRepository.save(profile);
    }

    @Cacheable(value = "profile", key="#id")
    public Optional<Profile> findById (Long id){
        return profileRepository.findById(id);
    }

    @CachePut(value = "profile", key="#profileId")
    public Profile updateCoverPhoto(MultipartFile image , Long profileId) throws IOException {

        Profile profile = profileRepository.findById(profileId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile Not Found"));

        if(profile.getCoverPhoto() != null)
            fileStorageService.deleteFileFromStorage(profile.getCoverPhoto(), BlobType.IMAGE);

        String source = fileStorageService.storeFile(image,profileId,BlobType.IMAGE);
        return profileRepository.save(profile);
    }

    @CachePut(value = "profile", key="#profileId")
    public Profile updateProfile(Long profileId, ProfileUpdateRequest request) throws IOException {
        app.print("Service: Updating profile info");
        app.print(request);
        Profile profile = profileRepository.findById(profileId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile Not Found"));

        if(request.getJobTitle() != null){
            profile.setJobTitle(request.getJobTitle());
        }
        if(request.getPricePerHour() != null)
            profile.setPricePerHour(request.getPricePerHour());
        if(request.getBio().length()>0) {
            if(profile.getBio().isEmpty()){
                app.print("Incrementing profile percentage complete");
                profile.incrementPercentageComplete(20);
                app.print("Profile percentage complete incremented");
            }
            profile.setBio(request.getBio());
        }
        if(request.getBio().length()==0){
            profile.setBio(request.getBio());
            app.print("Decrementing profile percentage complete");
            profile.decrementPercentageComplete(20);
            app.print("Profile percentage complete decremented");
        }
        if(request.getIsEmployer() != null)
            profile.setIsEmployer(request.getIsEmployer());
        if(request.getIsFreelancer() != null)
            profile.setIsFreelancer(request.getIsFreelancer());
        if (request.getProfileType() != null)
            profile.setProfileType(request.getProfileType());
        if(request.getPhoneNumber() != null){
            User user = userRepository.findById(profile.getUserId()).orElseThrow(
                    ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            user.setPhoneNumber(request.getPhoneNumber());
            userRepository.save(user);
        }

        return profileRepository.save(profile);
    }

    public Profile calculatePercentage(Long userId){

        int experienceValue = 0;
        int bioValue = 0;
        int skillValue = 0;
        int socialLinkValue = 0;
        int photoValue = 0;
        int qualificationValue = 0;
        int percentageComplete = 0;

        photoValue = checkPhotos(userId);
        app.print("PHOTO VALUE: " + photoValue);
        // Get the user profile
        Optional<Profile> profile = findByUserId(userId);
        Profile savedProfile = new Profile();

        if(profile.isPresent()){
            savedProfile = profile.get();
            // For profiles that have already been updated but do not have the percentage complete value set
            Long profileId = savedProfile.getId();
            experienceValue = experienceService.findByProfileId(profileId, Sort.by("createdAt").ascending()).size()==0 ? 0:20;
            app.print("EXPERIENCE VALUE: " + experienceValue);
            if(savedProfile.getBio()==null){
                app.print("BIO VALUE: " + bioValue);
            }
            else{
                bioValue = savedProfile.getBio().length()==0 ? 0:20;
                app.print("BIO VALUE: " + bioValue);
            }
            if (!qualificationService.findAllByProfileId(profileId, Sort.by("createdAt").ascending()).isEmpty()
                    || !trainingService.findAllByProfileId(profileId, Sort.by("createdAt").ascending()).isEmpty()){
                qualificationValue = 10;
            }
            app.print("QUALIFICATION VALUE: " + qualificationValue);
            skillValue = savedProfile.getSkills().size()==0 ? 0:20;
            app.print("SKILL VALUE: " + skillValue);
            socialLinkValue = socialLinkService.findAllByUserId(userId).size()==0 ? 0:10;
            app.print("SOCIAL LINK VALUE: " + socialLinkValue);
        }

        percentageComplete = experienceValue + bioValue + qualificationValue + photoValue + skillValue + socialLinkValue;
        savedProfile.setPercentageComplete(percentageComplete);

        app.print("PERCENTAGE COMPLETE SET");

        return save(savedProfile);
    }

    private int checkPhotos(Long userID){
        Optional<User> aUser = userService.findById(userID);
        User savedUser= new User();
        int percentageForPhotos = 0;

        if(aUser.isPresent()){
            savedUser = aUser.get();
        }

        app.print("BEFORE CALCULATING FOR IMAGE");

        if(savedUser.getImg()==null && savedUser.getCoverImg()==null){
            return percentageForPhotos;
        }

        if(savedUser.getImg()!=null || savedUser.getImg().length()!=0){
            app.print("CALCULATING FOR PROFILE IMAGE");
            percentageForPhotos += 15;
        }

        if(savedUser.getCoverImg()!=null || savedUser.getCoverImg().length()!=0){
            app.print("CALCULATING FOR COVER IMAGE");
            percentageForPhotos += 5;
        }

        return percentageForPhotos;
    }

    public String getPhoneNumberByProfileId(Long profileId){
        Profile profile = profileRepository.findById(profileId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile Not Found"));
        User user = userRepository.findById(profile.getUserId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
       return user.getPhoneNumber() == null ? "" : user.getPhoneNumber();
    }


    @CacheEvict(value = "user_profile", key="#userId")
    public void deleteAllByUserId(Long userId){
        profileRepository.deleteAllByUserId(userId);
    }

}
