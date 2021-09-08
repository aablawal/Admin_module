package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.Photo;
import com.unionbankng.future.authorizationserver.pojos.PhotoAndVideoRequest;
import com.unionbankng.future.authorizationserver.repositories.PhotoRepository;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class PhotoService {

    Logger logger = LoggerFactory.getLogger(PhotoService.class);
    private final PhotoRepository photoRepository;
    private final FileStorageService fileStorageService;

    public Page<Photo> findAllByProfileId(Long userId, Pageable pageable){
        return photoRepository.findByProfileId(userId,pageable);
    }

    public Optional<Photo> findById (Long id){
        return photoRepository.findById(id);
    }

    public void deleteById (Long id){
        Photo photo = photoRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo Not Found"));
        int status = fileStorageService.deleteFileFromStorage(photo.getSource(),BlobType.IMAGE);
        logger.info("GRPC photo delete status is {}",status);
        if(status != 200)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        photoRepository.deleteById(id);
    }

    public Photo saveFromRequest (MultipartFile file, PhotoAndVideoRequest request, Photo photo) throws IOException {
        photo.setProfileId(request.getProfileId());
        photo.setComment(request.getComment());
        if (file != null){
            String source = fileStorageService.storeFile(file, request.getProfileId(), BlobType.IMAGE);
            photo.setSource(source);
        }
        photo.setTitle(request.getTitle());
        return photoRepository.save(photo);
    }


}
