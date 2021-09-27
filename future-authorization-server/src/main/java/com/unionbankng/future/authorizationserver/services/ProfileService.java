package com.unionbankng.future.authorizationserver.services;
import com.unionbankng.future.authorizationserver.entities.Profile;
import com.unionbankng.future.authorizationserver.pojos.ProfileUpdateRequest;
import com.unionbankng.future.authorizationserver.repositories.ProfileRepository;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    private final FileStorageService fileStorageService;


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
        profile.setCoverPhoto(source);
        return profileRepository.save(profile);
    }

    @CachePut(value = "profile", key="#profileId")
    public Profile updateProfile(Long profileId, ProfileUpdateRequest request) throws IOException {

        Profile profile = profileRepository.findById(profileId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile Not Found"));

        if(request.getJobTitle() != null)
            profile.setJobTitle(request.getJobTitle());
        if(request.getPricePerHour() != null)
            profile.setPricePerHour(request.getPricePerHour());
        if(request.getBio() != null)
            request.setBio(request.getBio());
        if(request.getIsEmployer() != null)
            profile.setIsEmployer(request.getIsEmployer());
        if(request.getIsFreelancer() != null)
            profile.setIsFreelancer(request.getIsFreelancer());

        return profileRepository.save(profile);
    }


    @CacheEvict(value = "user_profile", key="#userId")
    public void deleteAllByUserId(Long userId){
        profileRepository.deleteAllByUserId(userId);
    }

}
