package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.Profile;
import com.unionbankng.future.authorizationserver.entities.Qualification;
import com.unionbankng.future.authorizationserver.entities.Training;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.EducationAndTrainingRequest;
import com.unionbankng.future.authorizationserver.pojos.QualificationRequest;
import com.unionbankng.future.authorizationserver.pojos.TrainingRequest;
import com.unionbankng.future.authorizationserver.repositories.ProfileRepository;
import com.unionbankng.future.authorizationserver.services.QualificationService;
import com.unionbankng.future.authorizationserver.services.TrainingService;
import com.unionbankng.future.authorizationserver.utils.App;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class QualificationsController {

    private final QualificationService qualificationService;
    private final TrainingService trainingService;
    private final ProfileRepository profileRepository;
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

    @PostMapping(value = "/v1/qualifications/update_existing")//,consumes = { "multipart/form-data" }
    public ResponseEntity<APIResponse<Qualification>> updateQualification(@Nullable  @RequestPart("file") MultipartFile file,@Valid @RequestPart QualificationRequest request)
            throws IOException {

        Qualification qualification = qualificationService.findById(request.getQualificationId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Qualification not found"));

        qualification = qualificationService.saveFromRequest(file,request,qualification);

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,qualification));

    }


    @DeleteMapping("/v1/qualifications/delete/{qualificationId}")
    public ResponseEntity<APIResponse> deleteQualification(@PathVariable Long qualificationId){
        qualificationService.deleteById(qualificationId);
        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,null));
    }


    @PostMapping(value = "/v1/qualification")
    public ResponseEntity<APIResponse<String>> addNewQualificationAndTraining(@Valid @RequestBody EducationAndTrainingRequest request)
            throws IOException {

        app.print("Qualification Controller: Adding new qualification/training");

        Profile profile = profileRepository.findByUserId(request.getUserId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile not found")
        );
        request.setProfileId(profile.getId());
        app.print(request);

        if(request.getSchool() != null && !request.getSchool().isBlank()){
            app.print("Qualification Controller: Adding new qualification");
            QualificationRequest qualificationRequest = new QualificationRequest();
            qualificationRequest.setProfileId(request.getProfileId());
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



    @GetMapping(value = "/v1/qualification/education/{userId}")
    public ResponseEntity<APIResponse<List<?>>> getAllQualificationByUserId(@PathVariable Long userId)
            throws IOException {

        app.print("Qualification Controller: fetching all users qualification");

        Profile profile = profileRepository.findByUserId(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile not found")
        );

        List<Qualification> qualifications = qualificationService.
                findAllByProfileId(profile.getId(), Sort.by("createdAt").ascending());

        List<EducationAndTrainingRequest> educationAndTrainingRequests = new ArrayList<>();

        for (Qualification qualification : qualifications) {
            EducationAndTrainingRequest educationAndTrainingRequest = new EducationAndTrainingRequest();
            educationAndTrainingRequest.setQualificationId(qualification.getId());
            educationAndTrainingRequest.setSchool(qualification.getSchool());
            educationAndTrainingRequest.setCountry(qualification.getSchool());
            educationAndTrainingRequest.setDegree(qualification.getDegree());
            educationAndTrainingRequest.setStartYear(qualification.getStartYear());
            educationAndTrainingRequest.setEndYear(qualification.getEndYear());
            educationAndTrainingRequests.add(educationAndTrainingRequest);
        }

        app.print("Qualification Controller: fetching all users qualification completed");
        app.print(educationAndTrainingRequests);

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true, educationAndTrainingRequests) );

    }


    @GetMapping(value = "/v1/qualification/training/{userId}")
    public ResponseEntity<APIResponse<List<?>>> getAllTrainingByUserId(@PathVariable Long userId) {

        app.print("Qualification Controller: fetching all users training");
        app.print(userId);

        Profile profile = profileRepository.findByUserId(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile not found")
        );


        List<Training> trainings = trainingService.
                findAllByProfileId(profile.getId(), Sort.by("createdAt").ascending());

        app.print("trainings");
        app.print(trainings);
        List<EducationAndTrainingRequest> educationAndTrainingRequests = new ArrayList<>();


        for (Training training : trainings) {
            EducationAndTrainingRequest educationAndTrainingRequest = new EducationAndTrainingRequest();
            educationAndTrainingRequest.setTrainingId(training.getId());
            educationAndTrainingRequest.setTrainingTitle(training.getTitle());
            educationAndTrainingRequest.setTrainingOrganization(training.getOrganization());
            educationAndTrainingRequest.setTrainingLinkOrId(training.getLinkOrId());
            educationAndTrainingRequest.setTrainingDescription(training.getDescription());
            educationAndTrainingRequest.setTrainingYearAwarded(training.getYearAwarded());

            educationAndTrainingRequests.add(educationAndTrainingRequest);
        }

        app.print("Qualification Controller: fetching all users training completed");
        app.print(educationAndTrainingRequests);

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true, educationAndTrainingRequests) );

    }


    @GetMapping(value = "/v1/qualification/training/{trainingId}")
    public ResponseEntity<APIResponse<List<?>>> deleteTrainingById(@PathVariable Long trainingId) {

        app.print("Qualification Controller: deleting training by id");

        trainingService.deleteById(trainingId);

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true, null) );

    }



    @PutMapping(value = "/v1/qualification")
    public ResponseEntity<APIResponse<String>> editQualificationAndTraining(@Valid @RequestBody EducationAndTrainingRequest request)
            throws IOException {

        app.print("Qualification Controller: editing qualification/training");

        Profile profile = profileRepository.findByUserId(request.getUserId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile not found")
        );
        request.setProfileId(profile.getId());
        app.print(request);

        if(request.getSchool() != null && !request.getSchool().isBlank()){
            app.print("Qualification Controller: Editing qualification");
            QualificationRequest qualificationRequest = new QualificationRequest();
            qualificationRequest.setProfileId(request.getProfileId());
            qualificationRequest.setUserId(request.getUserId());
            qualificationRequest.setSchool(request.getSchool());
            qualificationRequest.setCountry(request.getCountry());
            qualificationRequest.setDegree(request.getDegree());
            qualificationRequest.setStartYear(request.getStartYear());
            qualificationRequest.setEndYear(request.getEndYear());
            Qualification qualification = qualificationService.findById(request.getQualificationId()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Qualification not found")
            );
            Qualification updatedQualification = qualificationService.saveFromRequest(null, qualificationRequest, qualification);
            app.print(updatedQualification);
        }


        if(request.getTrainingTitle() != null && !request.getTrainingTitle().isBlank()){
            app.print("Qualification Controller: Editing Training");
            TrainingRequest trainingRequest = new TrainingRequest();
            trainingRequest.setUserId(request.getUserId());
            trainingRequest.setProfileId(request.getProfileId());
            trainingRequest.setTitle(request.getTrainingTitle());
            trainingRequest.setOrganization(request.getTrainingOrganization());
            trainingRequest.setLinkOrId(request.getTrainingLinkOrId());
            trainingRequest.setDescription(request.getTrainingDescription());
            trainingRequest.setYearAwarded(request.getTrainingYearAwarded());
            Training training = trainingService.findById(request.getTrainingId()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Training not found")
            );
            Training updatedTraining = trainingService.saveFromRequest(trainingRequest, training);
            app.print(updatedTraining);
        }

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true, "Request Successful") );

    }


}
