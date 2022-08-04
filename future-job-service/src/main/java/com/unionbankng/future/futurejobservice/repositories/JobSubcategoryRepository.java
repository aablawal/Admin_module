package com.unionbankng.future.futurejobservice.repositories;

import com.unionbankng.future.futurejobservice.entities.JobSubcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JobSubcategoryRepository extends JpaRepository<JobSubcategory,Long> {
    Optional<List<JobSubcategory>> findByTitle(String title);
    @Query(value="SELECT * FROM job_subcategories c WHERE (c.title like %:search% or c.description like %:search%)",nativeQuery = true)
    Optional<List<JobSubcategory>> findSubcategoryBySearch(String search);
    @Query(value = "SELECT TOP(40) * FROM job_subcategories c ORDER BY c.id DESC", nativeQuery = true)
    Optional<List<JobSubcategory>> findTopSubcategories();

}
