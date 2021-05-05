package com.unionbankng.future.futureloanservice.repositories;
import com.unionbankng.future.futureloanservice.entities.JobContractDispute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobContractDisputeRepository extends JpaRepository<JobContractDispute,Long> {
    JobContractDispute findByContractId(Long contractId);
}

