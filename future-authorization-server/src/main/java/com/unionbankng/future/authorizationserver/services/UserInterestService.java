package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.Tag;
import com.unionbankng.future.authorizationserver.entities.UserInterest;
import com.unionbankng.future.authorizationserver.pojos.EntitySkillRequest;
import com.unionbankng.future.authorizationserver.repositories.UserInterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserInterestService {

    private final UserInterestRepository userInterestRepository;

    public List<UserInterest> findAllByUserId (Long userId){
        return userInterestRepository.findAllByUserId(userId);
    }

    public Optional<UserInterest> findById (Long id){
        return userInterestRepository.findById(id);
    }

    public void deleteById (Long id){
        userInterestRepository.deleteById(id);
    }

    public UserInterest saveFromRequest (EntitySkillRequest request, UserInterest userInterest){
        Tag tag = new Tag();
        tag.setId(request.getSkillId());

        userInterest.setUserId(request.getEntityId());
        userInterest.setSkill(tag);

        return userInterestRepository.save(userInterest);
    }

}
