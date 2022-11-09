package com.unionbankng.future.authorizationserver.interfaces;

import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.RoleRequest;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface RoleService {

    ResponseEntity<APIResponse> createRoleWithNewPermission(String roleName, String roleFunction, Collection<String> name);

    ResponseEntity<APIResponse> createRole(RoleRequest request);

    ResponseEntity<APIResponse> updateRole(Long id,RoleRequest role);

    ResponseEntity<APIResponse> deleteRole(Long id);
}
