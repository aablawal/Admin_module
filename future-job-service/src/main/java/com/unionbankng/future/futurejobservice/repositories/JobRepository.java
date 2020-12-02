package com.unionbankng.future.futurejobservice.repositories;
import com.unionbankng.future.futurejobservice.entities.Job;
import com.unionbankng.future.futurejobservice.enums.JobType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {

    Optional<Job> findJobByInvitationId(String invitationId);
    @Query(value = "SELECT * FROM jobs j where j.oid=:id and j.status not in('IA','CA') order by id desc", nativeQuery = true)
    Page<Job> findByOid(Pageable pageable, Long id);

    @Query(value = "SELECT * FROM jobs j where j.type=:type and (j.status='AC' or (j.status='WP' and j.type='TEAMS_PROJECT')) order by id desc", nativeQuery = true)
    Page<Job> findByType(Pageable pageable, String type);

    @Query(value = "SELECT * FROM jobs j where j.oid=:id and j.status=:status order by id desc", nativeQuery = true)
    Page<Job> findJobsByOwnerIdAndStatus(Pageable pageable, Long id, String status);

    @Query(value = "SELECT * FROM jobs j WHERE (j.id in(SELECT jp.job_id FROM job_proposals jp where (jp.user_id=:id or jp.employer_id=:id) and jp.status=:status)  or j.oid=:id and status=:status)", nativeQuery = true)
    Page<Job> findJobsByUserIdAndStatus(Pageable pageable, Long id, String status);

}
