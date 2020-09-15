package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.Qualification;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.QualificationRequest;
import com.unionbankng.future.authorizationserver.services.QualificationService;
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
public class QualificationsController {

    private final QualificationService qualificationService;

    @GetMapping("/v1/qualifications/find_by_user_id/{userId}")
    public ResponseEntity<APIResponse> findQualificationsByUserId(@PathVariable Long userId,
                                                              @RequestParam int pageNo, @RequestParam int limit) {

        Page<Qualification> qualifications = qualificationService.
                findAllByUserId(userId,PageRequest.of(pageNo, limit, Sort.by("createdAt").ascending()));

        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,qualifications));

    }

    @PostMapping(value = "/v1/qualifications/create_new",consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse> addNewQualification(@Nullable @RequestPart("file") MultipartFile file, @RequestPart QualificationRequest request)
            throws IOException {

        Qualification qualification = qualificationService.saveFromRequest(file,request,new Qualification());
        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,qualification));

    }

    @PostMapping(value = "/v1/qualifications/update_existing",consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse> updateQualification(@Nullable  @RequestPart("file") MultipartFile file,@RequestPart QualificationRequest request)
            throws IOException {

        Qualification qualification = qualificationService.findById(request.getQualificationId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Qualification not found"));

        qualification = qualificationService.saveFromRequest(file,request,qualification);

        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,qualification));

    }

    @DeleteMapping("/v1/qualifications/delete/{photoId}")
    public ResponseEntity<APIResponse> deletePhoto(@PathVariable Long photoId){
        qualificationService.deleteById(photoId);
        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,null));
    }

}
