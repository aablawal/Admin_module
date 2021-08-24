package com.unionbankng.future.futuremessagingservice.repositories;

import com.unionbankng.future.futuremessagingservice.entities.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityLoggerRepository extends JpaRepository<ActivityLog,Long> {

}
