package com.unionbankng.future.futurejobservice.controllers;


import com.unionbankng.future.futurejobservice.entities.Skill;
import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import com.unionbankng.future.futurejobservice.services.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class SkillController {

    private final SkillService skillService;

    @PostMapping("/v1/job/skill/add")
    public ResponseEntity<APIResponse> addNewSkill(@RequestBody Skill skill){
        return ResponseEntity.ok().body(skillService.addSkill(skill));
    }

    @GetMapping("v1/job/skill/find-all")
    public ResponseEntity<APIResponse> findAllSkills(){
        return ResponseEntity.ok().body(skillService.getSkills());
    }
}
