package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.ProfileSkill;
import com.unionbankng.future.authorizationserver.entities.UserInterest;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.EntitySkillRequest;
import com.unionbankng.future.authorizationserver.services.UserInterestService;
import com.unionbankng.future.authorizationserver.services.UserSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class UserInterestController {

    private final UserInterestService userInterestService;

    @GetMapping("/v1/user_interest/find_by_user_id/{userId}")
    public ResponseEntity<APIResponse<List<UserInterest>>> findQualificationsByProfileId(@PathVariable Long userId,
                                                              @RequestParam int pageNo, @RequestParam int limit) {

        List<UserInterest> userInterests = userInterestService.
                findAllByUserId(userId);

        return ResponseEntity.ok().body(new APIResponse<List<UserInterest>>("Request Successful",true,userInterests));

    }

    @PostMapping("/v1/user_interest/create_new")
    public ResponseEntity<APIResponse<UserInterest>> addNewSkill(@Valid @RequestBody EntitySkillRequest request) {

        UserInterest userInterest = userInterestService.saveFromRequest(request,new UserInterest());
        return ResponseEntity.ok().body(new APIResponse("Request Successful",true, userInterest));

    }


    @DeleteMapping("/v1/user_interest/delete/{interestId}")
    public ResponseEntity<APIResponse<String>> deletePhoto(@PathVariable Long interestId){
        userInterestService.deleteById(interestId);
        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,null));
    }

}
