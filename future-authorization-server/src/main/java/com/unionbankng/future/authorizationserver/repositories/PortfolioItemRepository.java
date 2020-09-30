package com.unionbankng.future.authorizationserver.repositories;
import com.unionbankng.future.authorizationserver.entities.PortfolioItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PortfolioItemRepository extends JpaRepository<PortfolioItem, Long> {
    Page<PortfolioItem> findAllByProfileId(Long profileId, Pageable pageable);
}
