package com.unionbankng.future.authorizationserver.repositories;
import com.unionbankng.future.authorizationserver.entities.UserSkill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {
    Page<UserSkill> findByUserId(Long userId, Pageable pageable);
}
