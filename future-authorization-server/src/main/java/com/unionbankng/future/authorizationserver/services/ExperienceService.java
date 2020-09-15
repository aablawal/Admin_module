package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.Experience;
import com.unionbankng.future.authorizationserver.pojos.ExperienceRequest;
import com.unionbankng.future.authorizationserver.repositories.ExperienceRepository;
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
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final FileStorageService fileStorageService;

    public Page<Experience> findAllByUserId (Long userId, Pageable pageable){
        return experienceRepository.findByUserId(userId,pageable);
    }

    public Experience save (Experience experience){
        return experienceRepository.save(experience);
    }

    public Optional<Experience> findById (Long id){
        return experienceRepository.findById(id);
    }

    public void deleteById (Long id){

        Experience experience = experienceRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Experience Not Found"));
        int status = fileStorageService.deleteFileFromStorage(experience.getMedia(),BlobType.IMAGE);
        if(status != 200)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");

        experienceRepository.deleteById(id);
    }

    public Experience saveFromRequest (MultipartFile file,ExperienceRequest request, Experience experience) throws IOException {
        experience.setUserId(request.getUserId());
        experience.setCompany(request.getCompany());
        experience.setCurrent(request.getCurrent());
        experience.setDescription(request.getDescription());
        experience.setStartDate(request.getStartDate());
        experience.setEmploymentType(request.getEmploymentType());
        experience.setEndDate(request.getEndDate());
        experience.setHeadline(request.getHeadline());
        if (file != null){
            String source = fileStorageService.storeFile(file, request.getUserId(), BlobType.IMAGE);
            experience.setMedia(source);
        }
        experience.setTitle(request.getTitle());
        return experienceRepository.save(experience);
    }

}
