package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.PersonalInfoUpdateRequest;
import com.unionbankng.future.authorizationserver.services.ProfileService;
import com.unionbankng.future.authorizationserver.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class UsersController {

    private final UserService userService;
    private final ProfileService profileService;


    @PostMapping(value = "/v1/users/{userId}/upload_profile_image",consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse<User>> uploadProfileImage(@Nullable @RequestPart("image") MultipartFile image,
                                                          @PathVariable Long userId) throws IOException {

        User user = userService.updateProfileImage(image,userId);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,user));
    }

    @GetMapping("/v1/users/{userId}")
    public ResponseEntity<APIResponse<User>> getUserById(@PathVariable Long userId) {

        User user = userService.findById(userId).orElseThrow(  ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,user));
    }

    @PostMapping(value = "/v1/users/{userId}/update_profile")
    public ResponseEntity<APIResponse<User>> uploadProfileImage(@PathVariable Long userId, @Valid @RequestBody PersonalInfoUpdateRequest request) throws IOException {


        User user = userService.updatePersonalInfo(userId, request);

        return ResponseEntity.ok().body(new APIResponse<>("Profile updated successful",true,user));
    }

    @DeleteMapping("/v1/users/delete/{userId}")
    public ResponseEntity<APIResponse> deleteUser(@PathVariable  Long userId){

        profileService.deleteAllByUserId(userId);
        userService.deleteById(userId);
        return ResponseEntity.ok().body(new APIResponse("User deleted successful",true,null));
    }
}
