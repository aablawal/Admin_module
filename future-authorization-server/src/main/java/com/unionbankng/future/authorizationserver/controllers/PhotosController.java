package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.Photo;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.PhotoAndVideoRequest;
import com.unionbankng.future.authorizationserver.services.PhotoService;
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
public class PhotosController {

    private final PhotoService photoService;

    @GetMapping("/v1/photos/find_by_user_id/{userId}")
    public ResponseEntity<APIResponse> findExperienceByUserId(@PathVariable Long userId,
                                                              @RequestParam int pageNo, @RequestParam int limit) {

        Page<Photo> photos = photoService.findAllByUserId(userId,PageRequest.of(pageNo, limit, Sort.by("createdAt").ascending()));

        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,photos));

    }

    @PostMapping(value = "/v1/photos/create_new",consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse> addNewPhoto(@RequestPart("file") MultipartFile file, @RequestPart PhotoAndVideoRequest request) throws IOException {

        Photo photo = photoService.saveFromRequest(file,request,new Photo());
        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,photo));

    }

    @PostMapping(value = "/v1/photos/update_existing",consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse> updatePhoto(@Nullable @RequestPart("file") MultipartFile file,@RequestPart PhotoAndVideoRequest request) throws IOException {

        Photo photo = photoService.findById(request.getId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo not found")
        );

        photo = photoService.saveFromRequest(file,request,photo);

        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,photo));

    }

    @DeleteMapping("/v1/photos/delete/{photoId}")
    public ResponseEntity<APIResponse> deletePhoto(@PathVariable Long photoId){
        photoService.deleteById(photoId);
        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,null));
    }

}
