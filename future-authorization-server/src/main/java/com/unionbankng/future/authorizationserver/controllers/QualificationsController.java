package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.Qualification;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.QualificationRequest;
import com.unionbankng.future.authorizationserver.services.QualificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class QualificationsController {

    private final QualificationService qualificationService;

    @GetMapping("/v1/qualifications/find_by_user_id/{userId}")
    public ResponseEntity<APIResponse> findQualificationsByUserId(@PathVariable Long userId,
                                                              @RequestParam int pageNo, @RequestParam int limit) {

        Page<Qualification> qualifications = qualificationService.
                findAllByUserId(userId,PageRequest.of(pageNo, limit, Sort.by("createdAt").ascending()));

        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,qualifications));

    }

    @PostMapping("/v1/qualifications/create_new")
    public ResponseEntity<APIResponse> addNewQualification(@RequestBody QualificationRequest request) {

        Qualification qualification = qualificationService.saveFromRequest(request,new Qualification());
        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,qualification));

    }

    @PutMapping("/v1/qualifications/update_existing")
    public ResponseEntity<APIResponse> updateQualification(@RequestBody QualificationRequest request) {

        Qualification qualification = qualificationService.findById(request.getQualificationId()).orElse(null);

        if(qualification == null)
            return ResponseEntity.ok().body(new APIResponse("Qualification not found",false,null));

        qualification = qualificationService.saveFromRequest(request,qualification);

        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,qualification));

    }

    @DeleteMapping("/v1/qualifications/delete/{photoId}")
    public ResponseEntity<APIResponse> deletePhoto(@PathVariable Long photoId){
        qualificationService.deleteById(photoId);
        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,null));
    }

}
