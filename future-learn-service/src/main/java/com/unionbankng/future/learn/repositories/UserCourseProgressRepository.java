package com.unionbankng.future.learn.repositories;

import com.unionbankng.future.learn.entities.UserCourseProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCourseProgressRepository extends JpaRepository<UserCourseProgress,Long> {

    Optional<UserCourseProgress>findByCourseIdAndUserUUID(Long courseId,String userUUID);

}
