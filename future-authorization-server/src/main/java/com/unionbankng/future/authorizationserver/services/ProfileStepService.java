package com.unionbankng.future.authorizationserver.services;
import com.unionbankng.future.authorizationserver.entities.Profile;
import com.unionbankng.future.authorizationserver.repositories.ProfileRepository;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileStepService {

    private final ProfileRepository profileRepository;

    public void incrementPercentageComplete(Long userId, int percentage) {
        Profile profile = profileRepository.findByUserId(userId).orElse(null);
        if (profile != null) {
            int value = profile.getPercentageComplete() + percentage;
            profile.setPercentageComplete(value);
            profileRepository.save(profile);
        }
    }

    public void decrementPercentageComplete(Long userId, int percentage) {
        Profile profile = profileRepository.findByUserId(userId).orElse(null);
        if (profile != null) {
            int value = profile.getPercentageComplete() - percentage;
            profile.setPercentageComplete(value);
            profileRepository.save(profile);
        }
    }
}
