package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.Experience;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.ExperienceRequest;
import com.unionbankng.future.authorizationserver.services.ExperienceService;
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
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class ExperiencesController {

    private final ExperienceService experienceService;

    @GetMapping("/v1/experiences/find_by_user_id/{userId}")
    public ResponseEntity<APIResponse> findExperienceByUserId(@PathVariable Long userId,
                                                              @RequestParam int pageNo, @RequestParam int limit) {

        Page<Experience> experiences = experienceService.findAllByUserId(userId,PageRequest.of(pageNo, limit, Sort.by("startDate").ascending()));

        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,experiences));

    }

    @PostMapping(value = "/v1/experiences/create_new", consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse> addNewExperience(@Nullable @RequestPart("file") MultipartFile file, @RequestPart ExperienceRequest request)
            throws IOException {

        Experience experience = experienceService.saveFromRequest(file,request,new Experience());
        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,experience));

    }

    @PostMapping(value = "/v1/experiences/update_existing",consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse> updateExperience(@Nullable @RequestPart("file") MultipartFile file,@RequestPart ExperienceRequest request)
            throws IOException {

        Experience experience = experienceService.findById(request.getExperienceId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Experience not found")
        );

        experience = experienceService.saveFromRequest(file,request,experience);

        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,experience));

    }

    @DeleteMapping("/v1/experiences/delete/{experienceId}")
    public ResponseEntity<APIResponse> deleteExperience(@PathVariable Long experienceId){
        experienceService.deleteById(experienceId);
        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,null));
    }

}