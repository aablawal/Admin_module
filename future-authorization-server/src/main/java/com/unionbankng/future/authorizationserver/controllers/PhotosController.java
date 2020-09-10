package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.Photo;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.PhotoRequest;
import com.unionbankng.future.authorizationserver.services.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/v1/photos/create_new")
    public ResponseEntity<APIResponse> addNewPhoto(@RequestBody PhotoRequest request) {

        Photo photo = photoService.saveFromRequest(request,new Photo());
        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,photo));

    }

    @PutMapping("/v1/photos/update_existing")
    public ResponseEntity<APIResponse> updatePhoto(@RequestBody PhotoRequest request) {

        Photo photo = photoService.findById(request.getPhotoId()).orElse(null);

        if(photo == null)
            return ResponseEntity.ok().body(new APIResponse("Photo not found",false,null));

        photo = photoService.saveFromRequest(request,photo);

        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,photo));

    }

    @DeleteMapping("/v1/photos/delete/{photoId}")
    public ResponseEntity<APIResponse> deletePhoto(@PathVariable Long photoId){
        photoService.deleteById(photoId);
        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,null));
    }

}
