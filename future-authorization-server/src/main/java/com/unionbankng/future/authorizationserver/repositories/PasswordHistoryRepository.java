package com.unionbankng.future.authorizationserver.repositories;
import com.unionbankng.future.authorizationserver.entities.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {
    Optional<List<PasswordHistory>> findByUuid(String uuid);
}
