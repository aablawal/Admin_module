package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.Tag;
import com.unionbankng.future.authorizationserver.entities.ProfileSkill;
import com.unionbankng.future.authorizationserver.pojos.EntitySkillRequest;
import com.unionbankng.future.authorizationserver.repositories.UserSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSkillService {

    private final UserSkillRepository userSkillRepository;

    public Page<ProfileSkill> findAllByProfileId (Long profileId, Pageable pageable){
        return userSkillRepository.findAllByProfileId(profileId,pageable);
    }

    public Optional<ProfileSkill> findById (Long id){
        return userSkillRepository.findById(id);
    }

    public void deleteById (Long id){
        userSkillRepository.deleteById(id);
    }

    public ProfileSkill saveFromRequest (EntitySkillRequest request, ProfileSkill profileSkill){
        Tag tag = new Tag();
        tag.setId(request.getSkillId());

        profileSkill.setProfileId(request.getEntityId());
        profileSkill.setSkill(tag);

        return userSkillRepository.save(profileSkill);
    }

}
