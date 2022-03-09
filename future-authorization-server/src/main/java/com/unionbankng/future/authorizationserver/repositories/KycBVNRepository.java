package com.unionbankng.future.authorizationserver.repositories;

import com.unionbankng.future.authorizationserver.entities.KycBVNVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KycBVNRepository extends JpaRepository<KycBVNVerification,Long> {

    Optional<KycBVNVerification> findByUserId(Long id);
}