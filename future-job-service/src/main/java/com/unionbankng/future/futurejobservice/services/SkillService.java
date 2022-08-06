package com.unionbankng.future.futurejobservice.services;

import com.unionbankng.future.futurejobservice.entities.Skill;
import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import com.unionbankng.future.futurejobservice.repositories.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    public APIResponse<Skill> addSkill(Skill skill) {
        if (skill.getTitle() == null)
            return new APIResponse<>("Skill Title required", false, null);
        else {
            List<Skill> existingCategory = skillRepository.findByTitle(skill.getTitle()).orElse(null);
            if (existingCategory != null) {
                return new APIResponse<>("Skill already exists", false, null);
            } else {
                Skill savedSkill = skillRepository.save(skill);
                return new APIResponse<Skill>("success", true, savedSkill);
            }
        }
    }

    public APIResponse<List<Skill>> getSkills(){
        List<Skill> skills = skillRepository.findAll();
        return new APIResponse<List<Skill>>("success",true,skills);
    }
}
