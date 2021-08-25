package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.UserSocialMedia;
import com.unionbankng.future.authorizationserver.enums.SocialMedia;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.services.UserSocialMediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class UserSocialMediaController {

    private final UserSocialMediaService userSocialMediaService;

    @PostMapping("/v1/user_social_media/link_social")
    public ResponseEntity<APIResponse<UserSocialMedia>> linkSocialMedia(@NotNull @RequestParam Long userId, @NotNull @RequestParam SocialMedia socialMedia, @NotNull String socialCode)
    {

        UserSocialMedia userSocialMedia = userSocialMediaService.linkUserSocialMedia(userId,socialMedia,socialCode);

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,userSocialMedia));

    }

    @GetMapping("/v1/user_social_media/{userId}/get_social")
    public ResponseEntity<APIResponse<UserSocialMedia>> getSocialAccount(@PathVariable Long userId)
    {

        UserSocialMedia userSocialMedia = userSocialMediaService.findByUserId(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Social media account not found")
        );

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,userSocialMedia));

    }

    @GetMapping("/v1/user_social_media/{userId}/disconnect")
    public ResponseEntity<APIResponse<String>> disconnectSocial(@PathVariable Long userId)
    {

        userSocialMediaService.deleteAllByUserId(userId);

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,"Request successful"));

    }

}
