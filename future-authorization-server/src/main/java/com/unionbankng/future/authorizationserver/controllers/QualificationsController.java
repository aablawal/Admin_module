package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.Qualification;
import com.unionbankng.future.authorizationserver.entities.Training;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.EducationAndTrainingRequest;
import com.unionbankng.future.authorizationserver.pojos.QualificationRequest;
import com.unionbankng.future.authorizationserver.pojos.TrainingRequest;
import com.unionbankng.future.authorizationserver.services.QualificationService;
import com.unionbankng.future.authorizationserver.services.TrainingService;
import com.unionbankng.future.authorizationserver.utils.App;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
public class QualificationsController {

    private final QualificationService qualificationService;
    private final TrainingService trainingService;
    private final App app;

    @GetMapping("/v1/qualifications/find_by_profile_id/{profileId}")
    public ResponseEntity<APIResponse<List<Qualification>>> findQualificationsByProfileId(@PathVariable Long profileId) {

        List<Qualification> qualifications = qualificationService.
                findAllByProfileId(profileId,Sort.by("createdAt").ascending());

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true, qualifications));

    }


    @PostMapping(value = "/v1/qualifications/create_new",consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse<Qualification>> addNewQualification(@Nullable @RequestPart("file") MultipartFile file, @Valid @RequestBody QualificationRequest request)
            throws IOException {

        Qualification qualification = qualificationService.saveFromRequest(file,request,new Qualification());
        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,qualification));

    }

    @PostMapping(value = "/v1/qualifications/update_existing",consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse<Qualification>> updateQualification(@Nullable  @RequestPart("file") MultipartFile file,@Valid @RequestBody QualificationRequest request)
            throws IOException {

        Qualification qualification = qualificationService.findById(request.getQualificationId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Qualification not found"));

        qualification = qualificationService.saveFromRequest(file,request,qualification);

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,qualification));

    }

    @DeleteMapping("/v1/qualifications/delete/{photoId}")
    public ResponseEntity<APIResponse> deletePhoto(@PathVariable Long photoId){
        qualificationService.deleteById(photoId);
        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,null));
    }


    @PostMapping(value = "/v1/qualification")
    public ResponseEntity<APIResponse<String>> addNewQualificationAndTraining(@Valid @RequestBody EducationAndTrainingRequest request)
            throws IOException {

        app.print("Qualification COntroller: Adding new qualification/training");

        if(request.getSchool() != null && !request.getSchool().isBlank()){
            app.print("Qualification COntroller: Adding new qualification");
            QualificationRequest qualificationRequest = new QualificationRequest();
            qualificationRequest.setUserId(request.getUserId());
            qualificationRequest.setSchool(request.getSchool());
            qualificationRequest.setCountry(request.getCountry());
            qualificationRequest.setDegree(request.getDegree());
            qualificationRequest.setStartYear(request.getStartYear());
            qualificationRequest.setEndYear(request.getEndYear());
            Qualification qualification = qualificationService.saveFromRequest(null, qualificationRequest, new Qualification());
            app.print(qualification);
        }


        if(request.getTrainingTitle() != null && !request.getTrainingTitle().isBlank()){
            app.print("Qualification Controller: Adding new Training");
            TrainingRequest trainingRequest = new TrainingRequest();
            trainingRequest.setUserId(request.getUserId());
            trainingRequest.setProfileId(request.getProfileId());
            trainingRequest.setTitle(request.getTrainingTitle());
            trainingRequest.setOrganization(request.getTrainingOrganization());
            trainingRequest.setLinkOrId(request.getTrainingLinkOrId());
            trainingRequest.setDescription(request.getTrainingDescription());
            trainingRequest.setYearAwarded(request.getTrainingYearAwarded());

            Training training = trainingService.saveFromRequest(trainingRequest, new Training());
            app.print(training);
        }

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true, "Request Successful") );

    }

}
