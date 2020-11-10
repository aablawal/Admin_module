package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.ProfileSkill;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.EntitySkillRequest;
import com.unionbankng.future.authorizationserver.services.ProfileSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class ProfileSkillsController {

    private final ProfileSkillService profileSkillService;

    @GetMapping("/v1/user_skills/find_by_profile_id/{profileId}")
    public ResponseEntity<APIResponse<Page<ProfileSkill>>> findQualificationsByProfileId(@PathVariable Long profileId,
                                                              @RequestParam int pageNo, @RequestParam int limit) {

        Page<ProfileSkill> profileSkill = profileSkillService.
                findAllByProfileId(profileId,PageRequest.of(pageNo, limit, Sort.by("createdAt").ascending()));

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,profileSkill));

    }


    @PostMapping("/v1/user_skills/add_skills")
    public ResponseEntity<APIResponse<String>> addSkills(@Valid @RequestBody EntitySkillRequest request) {

        profileSkillService.saveSkillsFromRequest(request);
        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true, "Request Successful"));

    }


    @DeleteMapping("/v1/user_skills/delete/{userSkillId}")
    public ResponseEntity<APIResponse> deletePhoto(@PathVariable Long userSkillId){
        profileSkillService.deleteById(userSkillId);
        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,null));
    }

}
