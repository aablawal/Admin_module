package com.unionbankng.future.authorizationserver.repositories;

import com.unionbankng.future.authorizationserver.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findById(Long Long);
    Role findByRoleFunction(String name);
}
