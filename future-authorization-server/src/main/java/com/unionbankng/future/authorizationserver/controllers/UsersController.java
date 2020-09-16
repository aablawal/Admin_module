package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class UsersController {

    private final UserService userService;


    @PostMapping(value = "/v1/users/{userId}/upload_profile_image",consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse> uploadProfileImage(@Nullable @RequestPart("image") MultipartFile image,
                                                          @PathVariable Long userId) throws IOException {

        User user = userService.updateProfileImage(image,userId);

        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,user));
    }
}
