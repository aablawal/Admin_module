package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.Profile;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.ProfileUpdateRequest;
import com.unionbankng.future.authorizationserver.services.ProfileService;
import com.unionbankng.future.authorizationserver.utils.App;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final App app;

    @PostMapping(value = "/{profileId}/update-cover-photo", consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse<Profile>> updateCoverPhoto(@Nullable @RequestPart("image") MultipartFile image,
                                                        @PathVariable Long profileId) throws IOException {

        Profile profile = profileService.updateCoverPhoto(image,profileId);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,profile));
    }

    @GetMapping("/get-profile-by-user-id/{userId}")
    public ResponseEntity<APIResponse<RepresentationModel>> getProfileByUserId(@PathVariable Long userId) throws IOException {

        app.print("Profile Controller: Fetching user profile bu user Id");

        Profile profile = profileService.findByUserId(userId).orElseThrow(
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


        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,representationModel));
    }

    @PutMapping(value = "/{profileId}")
    public ResponseEntity<APIResponse<Profile>> updateProfile(@PathVariable Long profileId, @Valid @RequestBody ProfileUpdateRequest request) throws IOException {

        Profile profile = profileService.updateProfile(profileId, request);
        return ResponseEntity.ok().body(new APIResponse<>("Profile updated successful",true,profile));
    }



    @GetMapping(value = "")
    public ResponseEntity<APIResponse<Object>> getLoggedUserProfile() throws IOException {

        Object userId = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Object userId2 = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Object userId3 = SecurityContextHolder.getContext().getAuthentication().getDetails();
        app.print(userId);
        app.print(userId3);
        app.print("##########PRINTING SECURITY CREDENTIALS");
        app.print(userId.toString());
        app.print(userId3.toString());

        return ResponseEntity.ok().body(new APIResponse<>("Profile updated successful",true, userId));
    }

}
