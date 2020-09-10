package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.Experience;
import com.unionbankng.future.authorizationserver.entities.Photo;
import com.unionbankng.future.authorizationserver.pojos.ExperienceRequest;
import com.unionbankng.future.authorizationserver.pojos.PhotoRequest;
import com.unionbankng.future.authorizationserver.repositories.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;

    public Page<Photo> findAllByUserId (Long userId, Pageable pageable){
        return photoRepository.findByUserId(userId,pageable);
    }

    public Optional<Photo> findById (Long id){
        return photoRepository.findById(id);
    }

    public void deleteById (Long id){
        photoRepository.deleteById(id);
    }

    public Photo saveFromRequest (PhotoRequest request, Photo photo){
        photo.setUserId(request.getUserId());
        photo.setComment(request.getComment());
        photo.setSource(request.getSource());
        photo.setTitle(request.getTitle());
        return photoRepository.save(photo);
    }
}
