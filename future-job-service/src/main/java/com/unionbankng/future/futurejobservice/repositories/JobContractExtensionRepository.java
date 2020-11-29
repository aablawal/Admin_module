package com.unionbankng.future.futurejobservice.repositories;
import com.unionbankng.future.futurejobservice.entities.JobContractExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobContractExtensionRepository extends JpaRepository<JobContractExtension,Long> {

    @Query(value = "SELECT TOP(1) * FROM  job_contract_extension where proposal_id=:proposalId and job_id=:jobId and status='PE' order by created_at desc", nativeQuery = true)
    JobContractExtension findContractByProposalAndJobId(Long proposalId, Long jobId);

    @Query(value = "SELECT TOP(1) * FROM  job_contract_extension where proposal_id=:proposalId and employer_id=:userId and status='PE' order by created_at desc", nativeQuery = true)
    JobContractExtension findContractByProposalAndUserId(Long proposalId, Long userId);
}
