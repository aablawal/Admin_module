package com.unionbankng.future.futureutilityservice.repositories;
import com.unionbankng.future.futureutilityservice.entities.ActivityLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityLoggerRepository extends JpaRepository<ActivityLog,Long> {

    @Query(value = "SELECT  * FROM activity_logs u  where (username like %:q% or device like %:q% or ip_address like %:q% or description like %:q% )", nativeQuery = true)
    Page<ActivityLog> activityLogSearch(Pageable pageable, String q);

}
