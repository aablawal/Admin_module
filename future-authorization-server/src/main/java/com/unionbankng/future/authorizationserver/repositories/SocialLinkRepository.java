package com.unionbankng.future.authorizationserver.repositories;

import com.unionbankng.future.authorizationserver.entities.SocialLink;
import com.unionbankng.future.authorizationserver.entities.UserSocialMedia;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface SocialLinkRepository extends CrudRepository<SocialLink, Long> {
    Optional<SocialLink> findByUserId(Long userId);
    Boolean existsByUserId(Long userId);

    @Modifying
    @Query(value = "delete from SocialLink s where s.userId = ?1")
    void deleteAllByUserId(Long userId);

    Optional<List<SocialLink>> findAllByUserId(Long userId);
}
