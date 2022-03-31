package com.unionbankng.future.authorizationserver.services;
import com.unionbankng.future.authorizationserver.entities.Profile;
import com.unionbankng.future.authorizationserver.entities.Qualification;
import com.unionbankng.future.authorizationserver.pojos.QualificationRequest;
import com.unionbankng.future.authorizationserver.repositories.ProfileRepository;
import com.unionbankng.future.authorizationserver.repositories.QualificationRepository;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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
public class QualificationService {

    private final QualificationRepository qualificationRepository;
    private final ProfileRepository profileRepository;
    private final FileStorageService fileStorageService;

//    @Cacheable(value = "qualifications_by_profile", key="#profileId")
    public List<Qualification> findAllByProfileId(Long profileId, Sort sort){
        return qualificationRepository.findAllByProfileId(profileId,sort);
    }

//    @Cacheable(value = "qualifications_by_user", key="#userId")
    public List<Qualification> findAllByUserId(Long userId, Sort sort){
        Profile profile = profileRepository.findByUserId(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile not found")
        );
        return qualificationRepository.findAllByProfileId(profile.getId(), sort);
    }

    @Cacheable(value = "qualification", key="#id")
    public Optional<Qualification> findById (Long id){
        return qualificationRepository.findById(id);
    }

    @Caching(evict = {
            @CacheEvict(value = "qualifications_by_profile", allEntries = true),
            @CacheEvict(value = "qualification", key="#id")
    })
    public void deleteById (Long id)
    {
        Qualification qualification = qualificationRepository.findById(id).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Qualification not found"));

        if(qualification.getMedia() != null) {
            int status = fileStorageService.deleteFileFromStorage(qualification.getMedia(), BlobType.IMAGE);
            if (status != 200)
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
        qualificationRepository.deleteById(id);
    }

    @Caching(evict = {
            @CacheEvict(value = "qualifications_by_profile", key="#request.profileId"),
            @CacheEvict(value = "qualification", allEntries = true)
    })
    public Qualification saveFromRequest (MultipartFile file,QualificationRequest request, Qualification qualification) throws IOException {
        Profile profile = profileRepository.findByUserId(request.getUserId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile not found")
        );
        qualification.setProfileId(profile.getId());
        qualification.setActivities(request.getActivities());
        qualification.setSchool(request.getSchool());
        qualification.setDegree(request.getDegree());
        qualification.setDescription(request.getDescription());
        qualification.setEndYear(request.getEndYear());
        qualification.setStartYear(request.getStartYear());
        qualification.setFieldOfStudy(request.getFieldOfStudy());
        qualification.setSchool(request.getSchool());
        if (file != null){
            String source = fileStorageService.storeFile(file, request.getProfileId(), BlobType.IMAGE);
            qualification.setMedia(source);
        }

        return qualificationRepository.save(qualification);
    }

}
