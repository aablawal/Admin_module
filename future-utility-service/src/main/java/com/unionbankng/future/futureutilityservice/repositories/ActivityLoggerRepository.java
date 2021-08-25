package com.unionbankng.future.futureutilityservice.repositories;
import com.unionbankng.future.futureutilityservice.entities.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityLoggerRepository extends JpaRepository<ActivityLog,Long> {

}
