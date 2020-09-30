package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.Video;
import com.unionbankng.future.authorizationserver.pojos.PhotoAndVideoRequest;
import com.unionbankng.future.authorizationserver.repositories.VideoRepository;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final FileStorageService fileStorageService;

    public Page<Video> findAllByProfileId (Long profileId, Pageable pageable){
        return videoRepository.findAllByProfileId(profileId,pageable);
    }

    public Optional<Video> findById (Long id){
        return videoRepository.findById(id);
    }

    public void deleteById (Long id){
        Video video = videoRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video Not Found"));
        int status = fileStorageService.deleteFileFromStorage(video.getSource(),BlobType.VIDEO);
        if(status != 200)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        videoRepository.deleteById(id);
    }

    public Video saveFromRequest (MultipartFile file, PhotoAndVideoRequest request, Video video) throws IOException {
        video.setProfileId(request.getProfileId());
        video.setComment(request.getComment());
        if (file != null){
            String source = fileStorageService.storeFile(file, request.getProfileId(), BlobType.VIDEO);
            video.setSource(source);
        }
        video.setTitle(request.getTitle());
        return videoRepository.save(video);
    }


}
