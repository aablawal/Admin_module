package com.unionbankng.future.authorizationserver.repositories;

import com.unionbankng.future.authorizationserver.entities.UserSocialMedia;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserSocialMediaRepository extends CrudRepository<UserSocialMedia, Long> {
    Optional<UserSocialMedia> findByUserId(Long userId);
    Boolean existsByUserId(Long userId);
    void deleteAllByUserId(Long userId);
}
