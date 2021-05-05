package com.unionbankng.future.futureloanservice.repositories;
import com.unionbankng.future.futureloanservice.entities.JobTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobTeamRepository extends JpaRepository<JobTeam,Long> {
}
