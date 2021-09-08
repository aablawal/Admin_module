package com.unionbankng.future.learn.repositories;

import com.unionbankng.future.learn.entities.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor,Long> {

    Boolean existsByInstructorUUID(String instructorUUID);
    Optional<Instructor> findByInstructorUUID(String instructorUUID);
}
