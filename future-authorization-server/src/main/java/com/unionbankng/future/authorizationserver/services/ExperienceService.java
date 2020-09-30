package com.unionbankng.future.authorizationserver.services;

import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.unionbankng.future.authorizationserver.entities.Experience;
import com.unionbankng.future.authorizationserver.pojos.ExperienceRequest;
import com.unionbankng.future.authorizationserver.repositories.ExperienceRepository;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    @ReadThroughSingleCache(namespace = "experiences_by_profile", expiration = 0)
    public List<Experience> findByProfileId(@ParameterValueKeyProvider Long profileId, Sort sort){
        return experienceRepository.findByProfileId(profileId,sort);
    }

    public Experience save (Experience experience){
        return experienceRepository.save(experience);
    }

    @ReadThroughSingleCache(namespace = "experience", expiration = 0)
    public Optional<Experience> findById (@ParameterValueKeyProvider Long id){
        return experienceRepository.findById(id);
    }

    @InvalidateSingleCache(namespace = "experience")
    public void deleteById (@ParameterValueKeyProvider Long id){

        Experience experience = experienceRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Experience Not Found"));
        int status = fileStorageService.deleteFileFromStorage(experience.getMedia(),BlobType.IMAGE);
        if(status != 200)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");

        experienceRepository.deleteById(id);
    }

    @InvalidateSingleCache(namespace = "experience")
    public Experience saveFromRequest (MultipartFile file,ExperienceRequest request, Experience experience) throws IOException {
        experience.setProfileId(request.getProfileId());
        experience.setCompany(request.getCompany());
        experience.setCurrent(request.getCurrent());
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
