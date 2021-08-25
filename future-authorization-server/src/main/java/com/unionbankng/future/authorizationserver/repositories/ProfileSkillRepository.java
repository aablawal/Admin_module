package com.unionbankng.future.authorizationserver.repositories;
import com.unionbankng.future.authorizationserver.entities.ProfileSkill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProfileSkillRepository extends JpaRepository<ProfileSkill, Long> {
    Page<ProfileSkill> findAllByProfileId(Long profileId, Pageable pageable);
}
