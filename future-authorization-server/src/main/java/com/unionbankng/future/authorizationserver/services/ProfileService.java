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

    private final ProfileSkillService profileSkillService;
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
        if(request.getBio() != null) {
            if(profile.getBio() == null){
                app.print("Incrementing profile percentage complete");
                profile.incrementPercentageComplete(20);
                app.print("Profile percentage complete incremented");
            }
            profile.setBio(request.getBio());
        }
        if(request.getBio() == null){
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
        int profilePhotoValue = 0;
        int coverPhotoValue = 0;
        int qualificationValue = 0;
        int percentageComplete = 0;

        // Get the user profile
        Optional<Profile> profile = findByUserId(userId);
        Profile savedProfile = new Profile();

        if(profile.isPresent()){
            savedProfile = profile.get();
            // For profiles that have already been updated but do not have the percentage complete value set
            Long profileId = savedProfile.getId();
            experienceValue = experienceService.findByProfileId(profileId, Sort.by("createdAt").ascending()).isEmpty()? 0:20;
            bioValue = profile.get().getBio().isEmpty()? 0:20;
            if (!qualificationService.findAllByProfileId(profileId, Sort.by("createdAt").ascending()).isEmpty()
                    || !trainingService.findAllByProfileId(profileId, Sort.by("createdAt").ascending()).isEmpty()){
                qualificationValue = 10;
            }
            app.print("I GOT THE EXPERIENCE, BIOVALUE AND QUALIFICATION VALUE");
            profilePhotoValue = profile.get().getProfilePhoto().isEmpty() ? 0:15;
            coverPhotoValue = profile.get().getCoverPhoto().isEmpty() ? 0:5;
            skillValue = profile.get().getSkills().isEmpty() ? 0:20;
            app.print("THE ISSUE WAS FROM SKILL VALUE");
            socialLinkValue = socialLinkService.findAllByUserId(userId).isEmpty() ? 0:10;

            app.print("I GOT THE PROFILE VALUE, COVER PHOTO VALUE, SKILL VALUE AND SOCIAL LINK VALUE");
        }

        percentageComplete = experienceValue + bioValue + qualificationValue + profilePhotoValue + coverPhotoValue + skillValue + socialLinkValue;
        savedProfile.setPercentageComplete(percentageComplete);

        app.print("PERCENTAGE COMPLETE SET");

        return save(savedProfile);

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
