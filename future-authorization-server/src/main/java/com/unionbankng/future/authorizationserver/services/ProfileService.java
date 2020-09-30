package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.Experience;
import com.unionbankng.future.authorizationserver.entities.Profile;
import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.pojos.ExperienceRequest;
import com.unionbankng.future.authorizationserver.pojos.PersonalInfoUpdateRequest;
import com.unionbankng.future.authorizationserver.pojos.ProfileUpdateRequest;
import com.unionbankng.future.authorizationserver.repositories.ProfileRepository;
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
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final FileStorageService fileStorageService;


    public Optional<Profile> findByUserId (Long userId){
        return profileRepository.findByUserId(userId);
    }

    public Profile save (Profile profile){
        return profileRepository.save(profile);
    }

    public Optional<Profile> findById (Long id){
        return profileRepository.findById(id);
    }

    public Profile updateCoverPhoto(MultipartFile image , Long profileId) throws IOException {

        Profile profile = profileRepository.findById(profileId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile Not Found"));

        if(profile.getCoverPhoto() != null)
            fileStorageService.deleteFileFromStorage(profile.getCoverPhoto(), BlobType.IMAGE);

        String source = fileStorageService.storeFile(image,profileId,BlobType.IMAGE);
        profile.setCoverPhoto(source);
        return profileRepository.save(profile);
    }

    public Profile updateProfile(Long profileId, ProfileUpdateRequest request) throws IOException {

        Profile profile = profileRepository.findById(profileId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile Not Found"));

        if(request.getJobTitle() != null)
            profile.setJobTitle(request.getJobTitle());
        if(request.getPricePerHour() != null)
            profile.setPricePerHour(request.getPricePerHour());
        if(request.getBio() != null)
            request.setBio(request.getBio());
        if(request.getCoverPhoto() != null)
            request.setCoverPhoto(request.getCoverPhoto());
        if(request.getIsEmployer() != null)
            profile.setIsEmployer(request.getIsEmployer());
        if(request.getIsFreelancer() != null)
            profile.setIsFreelancer(request.getIsFreelancer());

        return profileRepository.save(profile);
    }

}
