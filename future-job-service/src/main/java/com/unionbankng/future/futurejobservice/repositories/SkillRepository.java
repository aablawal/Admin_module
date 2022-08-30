package com.unionbankng.future.futurejobservice.repositories;

import com.unionbankng.future.futurejobservice.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill,Long> {

    Optional<List<Skill>> findByTitle(String title);
}
