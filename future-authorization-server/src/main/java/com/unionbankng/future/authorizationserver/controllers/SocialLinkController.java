package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.UserSocialLink;
import com.unionbankng.future.authorizationserver.services.SocialLinkService;
import com.unionbankng.future.authorizationserver.utils.App;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1")
public class SocialLinkController {

    private final SocialLinkService socialLinkService;
    private final App app;


    @PostMapping("/social-link")
    public ResponseEntity<APIResponse<String>> addSocialLink(@RequestBody UserSocialLink userSocialLink){

        app.print("Adding Social link to user profile");
        socialLinkService.saveSocialLinkFromRequest(userSocialLink);

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true, "Request Successful"));

    }


    @GetMapping("/social-link/{userId}")
    public ResponseEntity<APIResponse<?>> getSocialLinks(@PathVariable Long userId) {

        app.print("Fetching user social links");

        UserSocialLink userSocialLink = socialLinkService.findUserSocialLinks(userId);

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true, userSocialLink));

    }

}
