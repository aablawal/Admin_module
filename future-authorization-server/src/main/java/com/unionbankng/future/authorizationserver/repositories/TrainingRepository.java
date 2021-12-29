package com.unionbankng.future.authorizationserver.repositories;

import com.unionbankng.future.authorizationserver.entities.Training;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findAllByProfileId(Long profileId, Sort sort);
}
