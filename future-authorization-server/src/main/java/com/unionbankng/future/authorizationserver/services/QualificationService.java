package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.Qualification;
import com.unionbankng.future.authorizationserver.pojos.QualificationRequest;
import com.unionbankng.future.authorizationserver.repositories.QualificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QualificationService {

    private final QualificationRepository qualificationRepository;

    public Page<Qualification> findAllByUserId (Long userId, Pageable pageable){
        return qualificationRepository.findByUserId(userId,pageable);
    }

    public Optional<Qualification> findById (Long id){
        return qualificationRepository.findById(id);
    }

    public void deleteById (Long id){
        qualificationRepository.deleteById(id);
    }

    public Qualification saveFromRequest (QualificationRequest request, Qualification qualification){
        qualification.setUserId(request.getUserId());
        qualification.setActivities(request.getActivities());
        qualification.setDegree(request.getDegree());
        qualification.setDescription(request.getDescription());
        qualification.setEndYear(request.getEndYear());
        qualification.setStartYear(request.getStartYear());
        qualification.setFieldOfStudy(request.getFieldOfStudy());
        qualification.setSchool(request.getSchool());
        qualification.setMedia(request.getMedia());
        return qualificationRepository.save(qualification);
    }

}
