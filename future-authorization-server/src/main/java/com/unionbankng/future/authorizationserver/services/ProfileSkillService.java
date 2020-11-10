package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.Tag;
import com.unionbankng.future.authorizationserver.entities.ProfileSkill;
import com.unionbankng.future.authorizationserver.pojos.EntitySkillRequest;
import com.unionbankng.future.authorizationserver.repositories.ProfileSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileSkillService {

    private final ProfileSkillRepository profileSkillRepository;

    public Page<ProfileSkill> findAllByProfileId (Long profileId, Pageable pageable){
        return profileSkillRepository.findAllByProfileId(profileId,pageable);
    }

    public Optional<ProfileSkill> findById (Long id){
        return profileSkillRepository.findById(id);
    }

    public void deleteById (Long id){
        profileSkillRepository.deleteById(id);
    }

    public void saveSkillsFromRequest (EntitySkillRequest request){

        List<ProfileSkill> profileSkills = new ArrayList<>();
        for (Long skillId : request.getSkillIds()){

            Tag tag = new Tag();
            tag.setId(skillId);

            ProfileSkill profileSkill = new ProfileSkill();
            profileSkill.setSkill(tag);
            profileSkill.setProfileId(request.getEntityId());
            profileSkills.add(profileSkill);
        }

        profileSkillRepository.saveAll(profileSkills);
    }


}
