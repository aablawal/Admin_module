package com.unionbankng.future.futurejobservice.repositories;
import com.unionbankng.future.futurejobservice.entities.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRateRepository extends JpaRepository<Rate,Long> {
    Rate findByUserId(Long userId);
}
