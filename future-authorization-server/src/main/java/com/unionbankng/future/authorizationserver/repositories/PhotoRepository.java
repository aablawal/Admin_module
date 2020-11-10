package com.unionbankng.future.authorizationserver.repositories;
import com.unionbankng.future.authorizationserver.entities.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Page<Photo> findByProfileId(Long profileId, Pageable pageable);
}
