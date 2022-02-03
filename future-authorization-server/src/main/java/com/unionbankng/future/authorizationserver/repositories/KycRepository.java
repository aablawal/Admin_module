package com.unionbankng.future.authorizationserver.repositories;
import com.unionbankng.future.authorizationserver.entities.Kyc;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface KycRepository extends JpaRepository<Kyc,Long> {
    Optional<Kyc> findByUserId(String userId);

}