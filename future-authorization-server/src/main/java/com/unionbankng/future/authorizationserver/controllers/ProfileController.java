package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.Profile;
import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.PersonalInfoUpdateRequest;
import com.unionbankng.future.authorizationserver.pojos.ProfileUpdateRequest;
import com.unionbankng.future.authorizationserver.services.ProfileService;
import com.unionbankng.future.authorizationserver.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
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
@RequestMapping(path = "api")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping(value = "/v1/profile/{profileId}/update_cover_photo",consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse<Profile>> updateCoverPhoto(@Nullable @RequestPart("image") MultipartFile image,
                                                        @PathVariable Long profileId) throws IOException {

        Profile profile = profileService.updateCoverPhoto(image,profileId);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,profile));
    }

    @GetMapping("/v1/profile/get_profile_by_user_id/{userId}")
    public ResponseEntity<APIResponse<RepresentationModel>> getProfileByUserId(@PathVariable Long userId) throws IOException {

        Profile profile = profileService.findByUserId(userId).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        RepresentationModel representationModel = RepresentationModel.of(profile);
        Link experiencesLink = linkTo(methodOn(ExperiencesController.class)
                .findExperienceByProfileId(profile.getId())).withRel("experiences");
        Link qualifications = linkTo(methodOn(QualificationsController.class)
                .findQualificationsByProfileId(profile.getId())).withRel("qualifications");
        Link Photos = linkTo(methodOn(PhotosController.class)
                .findExperienceByProfileId(profile.getId(),0,10)).withRel("photos");
        Link videos = linkTo(methodOn(VideosController.class)
                .findExperienceByProfileId(profile.getId(),0,10)).withRel("videos");
        Link skills = linkTo(methodOn(UserSkillsController.class)
                .findQualificationsByProfileId(profile.getId(),0,10)).withRel("skills");

        representationModel.add(experiencesLink);
        representationModel.add(qualifications);
        representationModel.add(Photos);
        representationModel.add(videos);
        representationModel.add(skills);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,representationModel));
    }

    @PostMapping(value = "/v1/profile/update_profile/{profileId}")
    public ResponseEntity<APIResponse<Profile>> uploadProfileImage(@PathVariable Long profileId, @Valid @RequestBody ProfileUpdateRequest request) throws IOException {


        Profile profile = profileService.updateProfile(profileId, request);

        return ResponseEntity.ok().body(new APIResponse<>("Profile updated successful",true,profile));
    }

}
