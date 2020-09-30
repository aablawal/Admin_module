package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.Qualification;
import com.unionbankng.future.authorizationserver.pojos.QualificationRequest;
import com.unionbankng.future.authorizationserver.repositories.QualificationRepository;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QualificationService {

    private final QualificationRepository qualificationRepository;
    private final FileStorageService fileStorageService;

    public Page<Qualification> findAllByProfileId(Long userId, Sort sort){
        return qualificationRepository.findAllByProfileId(userId,sort);
    }

    public Optional<Qualification> findById (Long id){
        return qualificationRepository.findById(id);
    }

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

    public Qualification saveFromRequest (MultipartFile file,QualificationRequest request, Qualification qualification) throws IOException {
        qualification.setProfileId(request.getProfileId());
        qualification.setActivities(request.getActivities());
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
