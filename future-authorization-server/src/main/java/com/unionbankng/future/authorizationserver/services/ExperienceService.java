package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.Experience;
import com.unionbankng.future.authorizationserver.pojos.ExperienceRequest;
import com.unionbankng.future.authorizationserver.repositories.ExperienceRepository;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final FileStorageService fileStorageService;


    @Cacheable(value = "experiences", key="#profileId")
    public List<Experience> findByProfileId(Long profileId, Sort sort){
        return experienceRepository.findByProfileId(profileId,sort);
    }

    @CacheEvict(value = "experiences", allEntries = true)
    public Experience save (Experience experience){
        return experienceRepository.save(experience);
    }

    @Cacheable(value = "experience", key="#id")
    public Optional<Experience> findById (Long id){
        return experienceRepository.findById(id);
    }

    @CacheEvict(value = "experience", key="#id")
    public void deleteById (Long id){

        Experience experience = experienceRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Experience Not Found"));
        int status = fileStorageService.deleteFileFromStorage(experience.getMedia(),BlobType.IMAGE);
        if(status != 200)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");

        experienceRepository.deleteById(id);
    }

    @CacheEvict(value = "experience", allEntries = true)
    public Experience saveFromRequest (MultipartFile file,ExperienceRequest request, Experience experience) throws IOException {
        experience.setProfileId(request.getProfileId());
        experience.setCompany(request.getCompany());
        experience.setIsCurrent(request.getCurrent());
        experience.setDescription(request.getDescription());
        experience.setStartDate(request.getStartDate());
        experience.setEmploymentType(request.getEmploymentType());
        experience.setEndDate(request.getEndDate());
        experience.setHeadline(request.getHeadline());
        if (file != null){
            String source = fileStorageService.storeFile(file, request.getProfileId(), BlobType.IMAGE);
            experience.setMedia(source);
        }
        experience.setTitle(request.getTitle());
        return experienceRepository.save(experience);
    }

}
