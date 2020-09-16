package com.unionbankng.future.authorizationserver.repositories;
import com.unionbankng.future.authorizationserver.entities.Qualification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QualificationRepository extends JpaRepository<Qualification, Long> {
    Page<Qualification> findByUserId(Long userId, Pageable pageable);
}
