package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.Experience;
import com.unionbankng.future.authorizationserver.entities.Tag;
import com.unionbankng.future.authorizationserver.entities.UserSkill;
import com.unionbankng.future.authorizationserver.pojos.ExperienceRequest;
import com.unionbankng.future.authorizationserver.pojos.UserSkillRequest;
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

    public Page<UserSkill> findAllByUserId (Long userId, Pageable pageable){
        return userSkillRepository.findByUserId(userId,pageable);
    }

    public Optional<UserSkill> findById (Long id){
        return userSkillRepository.findById(id);
    }

    public void deleteById (Long id){
        userSkillRepository.deleteById(id);
    }

    public UserSkill saveFromRequest (UserSkillRequest request, UserSkill userSkill){
        Tag tag = new Tag();
        tag.setId(request.getSkillId());

        userSkill.setUserId(request.getUserId());
        userSkill.setSkill(tag);

        return userSkillRepository.save(userSkill);
    }

}
