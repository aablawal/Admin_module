package com.unionbankng.future.authorizationserver.repositories;
import com.unionbankng.future.authorizationserver.entities.Experience;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByProfileId(Long profileId, Sort sort);
}
