package com.unionbankng.future.learn.repositories;

import com.unionbankng.future.learn.entities.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {

    Page<Announcement> findAllByCourseId(Long courseId, Pageable pageable);
}
