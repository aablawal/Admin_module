package com.unionbankng.future.learn.repositories;
import com.unionbankng.future.learn.entities.UserCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCourseRepository extends JpaRepository<UserCourse,Long> {

    Boolean existByUserUUIDAndCourseId(String userUUID,Long courseId);
    Boolean existByUserUUID(String userUUID);
    List<UserCourse> findAllByUserUUID(String userUUID);
    Long countAllByUserUUID(String userUUID);
    Page<UserCourse> findAllByCourseId(Long courseId, Pageable pageable);
    Long countAllByCourseId(Long courseId);
}
