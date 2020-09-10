package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.UserSkill;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.UserSkillRequest;
import com.unionbankng.future.authorizationserver.services.UserSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class UserSkillsController {

    private final UserSkillService userSkillService;

    @GetMapping("/v1/user_skills/find_by_user_id/{userId}")
    public ResponseEntity<APIResponse> findQualificationsByUserId(@PathVariable Long userId,
                                                              @RequestParam int pageNo, @RequestParam int limit) {

        Page<UserSkill> userSkills = userSkillService.
                findAllByUserId(userId,PageRequest.of(pageNo, limit, Sort.by("createdAt").ascending()));

        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,userSkills));

    }

    @PostMapping("/v1/user_skills/create_new")
    public ResponseEntity<APIResponse> addNewSkill(@RequestBody UserSkillRequest request) {

        UserSkill userSkill = userSkillService.saveFromRequest(request,new UserSkill());
        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,userSkill));

    }


    @DeleteMapping("/v1/user_skills/delete/{userSkillId}")
    public ResponseEntity<APIResponse> deletePhoto(@PathVariable Long userSkillId){
        userSkillService.deleteById(userSkillId);
        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,null));
    }

}
