package com.unionbankng.future.futureloanservice.repositories;
import com.unionbankng.future.futureloanservice.entities.JobPayment;
import com.unionbankng.future.futureloanservice.entities.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobPaymentRepository extends JpaRepository<JobPayment,Long> {
   Optional<JobPayment> findByPaymentReference(String paymentReference);
}

