package com.unionbankng.future.futurejobservice.repositories;
import com.unionbankng.future.futurejobservice.entities.JobContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobContractRepository extends JpaRepository<JobContract, Long> {

    @Query(value = "SELECT TOP(1) * FROM  job_contracts where proposal_id=:proposalId and job_id=:jobId order by created_at desc", nativeQuery = true)
    JobContract findContractByProposalAndJobId(Long proposalId, Long jobId);
}
