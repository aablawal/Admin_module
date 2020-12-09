package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.Video;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.PhotoAndVideoRequest;
import com.unionbankng.future.authorizationserver.services.VideoService;
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
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class VideosController {

    private final VideoService videoService;

    @GetMapping("/v1/videos/find_by_profile_id/{profileId}")
    public ResponseEntity<APIResponse<Page<Video>>> findExperienceByProfileId(@PathVariable Long profileId,
                                                              @RequestParam int pageNo, @RequestParam int limit) {

        Page<Video> videos = videoService.findAllByProfileId(profileId,PageRequest.of(pageNo, limit, Sort.by("createdAt").ascending()));

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,videos));

    }

    @PostMapping(value = "/v1/videos/create_new",consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse<Video>> addNewPhoto(@RequestPart("file") MultipartFile file, @Valid @RequestPart PhotoAndVideoRequest request) throws IOException {

        Video videos = videoService.saveFromRequest(file,request,new Video());
        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,videos));

    }

    @PostMapping(value = "/v1/videos/update_existing",consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse<Video>> updatePhoto(@Nullable @RequestPart("file") MultipartFile file,@Valid @RequestPart PhotoAndVideoRequest request) throws IOException {

        Video video = videoService.findById(request.getId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found")
        );

        video = videoService.saveFromRequest(file,request,video);

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,video));

    }

    @DeleteMapping("/v1/videos/delete/{photoId}")
    public ResponseEntity<APIResponse> deletePhoto(@PathVariable Long photoId){
        videoService.deleteById(photoId);
        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,null));
    }

}
