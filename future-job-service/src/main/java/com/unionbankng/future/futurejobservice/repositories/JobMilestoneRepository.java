package com.unionbankng.future.futurejobservice.repositories;
import com.unionbankng.future.futurejobservice.entities.JobContractExtension;
import com.unionbankng.future.futurejobservice.entities.JobMilestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobMilestoneRepository extends JpaRepository<JobMilestone,Long> {

    @Query(value = "SELECT TOP(1) * FROM  job_milestones where proposal_id=:proposalId and c=:jobId order by created_at desc", nativeQuery = true)
    JobMilestone findMilestoneByProposalAndJobId(Long proposalId, Long jobId);

    @Query(value = "SELECT TOP(1) * FROM  job_milestones where proposal_id=:proposalId and employer_id=:userId order by created_at desc", nativeQuery = true)
    JobMilestone findMilestoneByProposalAndUserId(Long proposalId, Long userId);

    @Query(value = "SELECT * FROM  job_milestones where proposal_id=:proposalId and job_id=:job_id and status=:status order by created_at desc", nativeQuery = true)
    List<JobMilestone> findAllMilestonesByProposalJobAndStatus(Long proposalId, Long job_id, String status);

    @Query(value = "SELECT * FROM  job_milestones where proposal_id=:proposalId and job_id=:job_id order by created_at desc", nativeQuery = true)
    List<JobMilestone> findAllMilestonesByProposalAndJobId(Long proposalId, Long job_id);
}
