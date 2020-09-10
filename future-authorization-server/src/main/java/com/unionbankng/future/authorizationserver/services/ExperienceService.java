package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.Experience;
import com.unionbankng.future.authorizationserver.pojos.ExperienceRequest;
import com.unionbankng.future.authorizationserver.repositories.ExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final ExperienceRepository experienceRepository;

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
        experienceRepository.deleteById(id);
    }

    public Experience saveFromRequest (ExperienceRequest request,Experience experience){
        experience.setUserId(request.getUserId());
        experience.setCompany(request.getCompany());
        experience.setCurrent(request.getCurrent());
        experience.setDescription(request.getDescription());
        experience.setStartDate(request.getStartDate());
        experience.setEmploymentType(request.getEmploymentType());
        experience.setEndDate(request.getEndDate());
        experience.setHeadline(request.getHeadline());
        experience.setMedia(request.getMedia());
        experience.setTitle(request.getTitle());
        return experienceRepository.save(experience);
    }

}
