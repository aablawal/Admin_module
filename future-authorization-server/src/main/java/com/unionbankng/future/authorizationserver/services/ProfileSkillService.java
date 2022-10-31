package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.Profile;
import com.unionbankng.future.authorizationserver.entities.Tag;
import com.unionbankng.future.authorizationserver.entities.ProfileSkill;
import com.unionbankng.future.authorizationserver.pojos.EntitySkillRequest;
import com.unionbankng.future.authorizationserver.pojos.ProfileSkillRequest;
import com.unionbankng.future.authorizationserver.repositories.ProfileRepository;
import com.unionbankng.future.authorizationserver.repositories.ProfileSkillRepository;
import com.unionbankng.future.authorizationserver.utils.App;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProfileSkillService {

    private final ProfileSkillRepository profileSkillRepository;
    private final ProfileRepository profileRepository;
    private final App app;

    private  final  ProfileStepService profileStepService;

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


    public void saveProfileSkills(ProfileSkillRequest request) {
        app.print("Profile Skill Service: Saving Skills");

        Profile profile = profileRepository.findByUserId(request.getUserId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile Not Found"));

        if(profile.getSkills().size()!=0){
            profile.getSkills().clear();
            app.print("Decrementing profile percentage complete");
            profileStepService.decrementPercentageComplete(profile.getId(),20);
            app.print("profile percentage complete decremented");
        }
        for(String skill : request.getSkills()){
            ProfileSkill profileSkill = new ProfileSkill();
            profileSkill.setSkillName(skill);
            profileSkill.setProfileId(profile.getId());
            profileSkillRepository.save(profileSkill);
            profile.getSkills().add(profileSkill);
            profileRepository.save(profile);
        }

        profileStepService.incrementPercentageComplete(profile.getId(),20);

    }


    public List<String> getProfileSkills(Long userId) {
        app.print("Profile Skill Service: Getting Skills");

        Profile profile = profileRepository.findByUserId(userId).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile Not Found"));

        Set<ProfileSkill> userSkills = profile.getSkills();

        List<String> profileSkillRequests = new ArrayList<>();
        for (ProfileSkill userSkill : userSkills) {
            profileSkillRequests.add(userSkill.getSkillName());
        }

        return profileSkillRequests;

    }
}
