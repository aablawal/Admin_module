package com.unionbankng.future.futurejobservice.repositories;

import com.unionbankng.future.futurejobservice.entities.Bundle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BundleRepository extends JpaRepository<Bundle, Long> {

    Page<Bundle> findAll(Pageable pageable);
}
