package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.Profile;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.ProfileUpdateRequest;
import com.unionbankng.future.authorizationserver.repositories.ProfileRepository;
import com.unionbankng.future.authorizationserver.services.ProfileService;
import com.unionbankng.future.authorizationserver.utils.App;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.io.IOException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileRepository profileRepository;
    private final App app;

    @PostMapping(value = "/{profileId}/update-cover-photo", consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse<Profile>> updateCoverPhoto(@Nullable @RequestPart("image") MultipartFile image,
                                                        @PathVariable Long profileId) throws IOException {

        Profile profile = profileService.updateCoverPhoto(image,profileId);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,profile));
    }

    @GetMapping("/get-profile-by-user-id/{userId}")
    public ResponseEntity<APIResponse<RepresentationModel>> getProfileByUserId(@PathVariable Long userId) throws IOException {

        app.print("Profile Controller: Fetching user profile by user Id");

        Long profileId = profileRepository.findByUserId(userId).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found")).getId();


        Profile profile = profileService.findById(profileId).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        RepresentationModel representationModel = RepresentationModel.of(profile);
        Link experiencesLink = linkTo(methodOn(ExperiencesController.class)
                .findExperienceByProfileId(profile.getId())).withRel("experiences");
        Link portfolioLink = linkTo(methodOn(PortfolioItemsController.class)
                .findPortfolioItemByProfileId(profile.getId(),0,10)).withRel("portfolio");
        Link qualifications = linkTo(methodOn(QualificationsController.class)
                .findQualificationsByProfileId(profile.getId())).withRel("qualifications");
        Link Photos = linkTo(methodOn(PhotosController.class)
                .findExperienceByProfileId(profile.getId(),0,10)).withRel("photos");
        Link videos = linkTo(methodOn(VideosController.class)
                .findExperienceByProfileId(profile.getId(),0,10)).withRel("videos");
        Link skills = linkTo(methodOn(ProfileSkillsController.class)
                .findQualificationsByProfileId(profile.getId(),0,10)).withRel("skills");
        String phoneNumber = profileService.getPhoneNumberByProfileId(profile.getId());

        representationModel.add(experiencesLink,portfolioLink,qualifications,Photos,videos,skills);

        app.print("Finished fetching user profile");
//        app.print(representationModel);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,representationModel));
    }

    @PutMapping(value = "/{profileId}")
    public ResponseEntity<APIResponse<Profile>> updateProfile(@PathVariable Long profileId, @Valid @RequestBody ProfileUpdateRequest request) throws IOException {

        Profile profile = profileService.updateProfile(profileId, request);
        return ResponseEntity.ok().body(new APIResponse<>("Profile updated successful",true,profile));
    }


    @PostMapping(value = "/update-by-userid/{userId}")
    public ResponseEntity<APIResponse<Profile>> updateProfileByUserId(@PathVariable Long userId, @Valid @RequestBody ProfileUpdateRequest request) throws IOException {

        app.print("Updating user profile by Id with request: ");
        Long profileId = profileRepository.findByUserId(userId).get().getId();

        app.print("Profile Id is "+ profileId.toString());

        Profile profile = profileService.updateProfile(profileId, request);
        app.print("Finished updating user profile");
        return ResponseEntity.ok().body(new APIResponse<>("Profile updated successful",true,profile));
    }

}