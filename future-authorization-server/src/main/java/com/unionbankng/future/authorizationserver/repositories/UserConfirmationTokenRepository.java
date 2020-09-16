package com.unionbankng.future.authorizationserver.repositories;

import com.unionbankng.future.authorizationserver.entities.UserConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserConfirmationTokenRepository extends JpaRepository<UserConfirmationToken, Long> {
    Optional<UserConfirmationToken> findByToken(String email);
}
