package com.unionbankng.future.authorizationserver.repositories;
import com.unionbankng.future.authorizationserver.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

}
