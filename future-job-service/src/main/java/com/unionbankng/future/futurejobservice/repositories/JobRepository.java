package com.unionbankng.future.futurejobservice.repositories;
import com.unionbankng.future.futurejobservice.entities.Job;
import com.unionbankng.future.futurejobservice.enums.JobStatus;
import com.unionbankng.future.futurejobservice.enums.JobType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {

    Optional<List<Job>> findByOid(Long id,  Sort sort);
    Optional<List<Job>> findByStatus(JobStatus status, Sort sort);
    Optional<List<Job>> findByCategories(String category,  Sort sort);
    Optional<List<Job>> findByType(JobType type,  Sort sort);
}