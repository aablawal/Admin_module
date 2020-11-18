package com.unionbankng.future.futurejobservice.repositories;
import com.unionbankng.future.futurejobservice.entities.JobProposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobProposalRepository extends JpaRepository<JobProposal, Long> {
    Page<JobProposal> findAllByJobId(Pageable pageable, Long jid);
    Page<JobProposal> findByUserId(Pageable pageable, Long userid);
    @Query(value = "SELECT count(*) FROM applications p where p.job_id=:jobId", nativeQuery = true)
    Long getCountByJobId(Long jobId);
}
