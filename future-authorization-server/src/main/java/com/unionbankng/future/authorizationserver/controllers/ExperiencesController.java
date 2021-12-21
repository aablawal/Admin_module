package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.Experience;
import com.unionbankng.future.authorizationserver.entities.PortfolioItem;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.ExperienceRequest;
import com.unionbankng.future.authorizationserver.pojos.PortfolioItemRequest;
import com.unionbankng.future.authorizationserver.services.ExperienceService;
import com.unionbankng.future.authorizationserver.utils.App;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class ExperiencesController {

    private final ExperienceService experienceService;
    private final App app;

    @GetMapping("/v1/experiences/find_by_profile_id/{profileId}")
    public ResponseEntity<APIResponse<List<Experience>>> findExperienceByProfileId(@PathVariable Long profileId) {

        List<Experience> experiences = experienceService.findByProfileId(profileId,Sort.by("startDate").ascending());

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful", true, experiences));

    }


    @GetMapping("/v1/experiences/{userId}")
    public ResponseEntity<APIResponse<List<Experience>>> findExperienceByUserId(@PathVariable Long userId) {


        app.print("Experience Controller: Fetching List of USer experience");

        List<Experience> experiences = experienceService.findByUserId(userId,Sort.by("startDate").ascending());

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful", true, experiences));

    }


    @PostMapping(value = "/v1/experiences/create-new")
    public ResponseEntity<APIResponse<Experience>> addNewExperience(@Valid @RequestBody ExperienceRequest request)
            throws IOException {

        app.print("Experience Controller: Printing Experience Request");
        app.print(request);

        Experience experience = experienceService.saveFromRequest(null,request,new Experience());
        return ResponseEntity.ok().body(new APIResponse<Experience>("Request Successful",true,experience));

    }



    @PostMapping(value = "/v1/experiences/update_existing",consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse<Experience>> updateExperience(@Nullable @RequestPart("file") MultipartFile file,@Valid @RequestPart ExperienceRequest request)
            throws IOException {

        Experience experience = experienceService.findById(request.getExperienceId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Experience not found")
        );

        experience = experienceService.saveFromRequest(file,request,experience);
        return ResponseEntity.ok().body(new APIResponse<Experience>("Request Successful",true,experience));
    }


    @DeleteMapping("/v1/experiences/delete/{experienceId}")
    public ResponseEntity<APIResponse<String>> deleteExperience(@PathVariable Long experienceId){
        experienceService.deleteById(experienceId);
        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,null));
    }

}
