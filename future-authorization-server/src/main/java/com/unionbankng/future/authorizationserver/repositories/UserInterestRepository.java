package com.unionbankng.future.authorizationserver.repositories;
import com.unionbankng.future.authorizationserver.entities.ProfileSkill;
import com.unionbankng.future.authorizationserver.entities.UserInterest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {
    List<UserInterest> findAllByUserId(Long userId);
}
