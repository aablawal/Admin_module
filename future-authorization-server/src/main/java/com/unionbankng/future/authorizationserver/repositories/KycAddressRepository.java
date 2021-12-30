package com.unionbankng.future.authorizationserver.repositories;

import com.unionbankng.future.authorizationserver.entities.KycAddressVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KycAddressRepository extends JpaRepository<KycAddressVerification,Long> {
    Optional<KycAddressVerification> findByResid(Long resID);
}