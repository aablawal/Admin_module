package com.unionbankng.future.authorizationserver.services;
import com.unionbankng.future.authorizationserver.entities.Profile;
import com.unionbankng.future.authorizationserver.entities.Qualification;
import com.unionbankng.future.authorizationserver.entities.Training;
import com.unionbankng.future.authorizationserver.pojos.QualificationRequest;
import com.unionbankng.future.authorizationserver.pojos.TrainingRequest;
import com.unionbankng.future.authorizationserver.repositories.ProfileRepository;
import com.unionbankng.future.authorizationserver.repositories.QualificationRepository;
import com.unionbankng.future.authorizationserver.repositories.TrainingRepository;
import com.unionbankng.future.authorizationserver.utils.App;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final ProfileRepository profileRepository;

    private final App app;

    @Cacheable(value = "trainings_by_profile", key="#profileId")
    public List<Training> findAllByProfileId(Long profileId, Sort sort){
        return trainingRepository.findAllByProfileId(profileId,sort);
    }


    @Cacheable(value = "trainings_by_user", key="#userId")
    public List<Training> findAllByUserId(Long userId, Sort sort){
        Profile profile = profileRepository.findByUserId(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile not found")
        );
        return trainingRepository.findAllByProfileId(profile.getId(), sort);
    }

    @Cacheable(value = "training", key="#id")
    public Optional<Training> findById (Long id){
        return trainingRepository.findById(id);
    }


    @Caching(evict = {
            @CacheEvict(value = "trainings_by_profile", allEntries = true),
            @CacheEvict(value = "training", key="#id")
    })
    public void deleteById (Long id) {
        Training training = trainingRepository.findById(id).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Training not found"));

        Profile profile = profileRepository.findById(training.getProfileId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile not found")
        );

        trainingRepository.deleteById(id);
        app.print("Training deleted");
        app.print("Decrementing profile percentage completed");
        profile.decrementPercentageComplete(10);
        profileRepository.save(profile);
        app.print("Profile percentage completed decreased");
    }


    @Caching(evict = {
            @CacheEvict(value = "trainings_by_profile", key="#request.profileId"),
            @CacheEvict(value = "training", allEntries = true)
    })
    public Training saveFromRequest (TrainingRequest request, Training training) throws IOException {

        Profile profile = profileRepository.findByUserId(request.getUserId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile not found")
        );
        training.setProfileId(profile.getId());
        training.setTitle(request.getTitle());
        training.setDescription(request.getDescription());
        training.setOrganization(request.getOrganization());
        training.setLinkOrId(request.getLinkOrId());
        training.setYearAwarded(request.getYearAwarded());
        if(trainingRepository.findAllByProfileId(profile.getId(),Sort.by("createdAt").ascending()).isEmpty()){
            app.print("Incrementing profile percentage complete");
            profile.incrementPercentageComplete(10);
            profileRepository.save(profile);
            app.print("profile percentage complete incremented");
        }
        return trainingRepository.save(training);
    }

}
