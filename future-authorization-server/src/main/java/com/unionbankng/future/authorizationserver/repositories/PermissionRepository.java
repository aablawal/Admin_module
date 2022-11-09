package com.unionbankng.future.authorizationserver.repositories;

import com.unionbankng.future.authorizationserver.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Permission findByName(String name);
}
