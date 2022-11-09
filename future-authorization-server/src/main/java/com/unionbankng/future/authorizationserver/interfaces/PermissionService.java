package com.unionbankng.future.authorizationserver.interfaces;

import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.PermissionRequest;
import org.springframework.http.ResponseEntity;

public interface PermissionService {

    ResponseEntity<APIResponse> createPermission(PermissionRequest request );

    ResponseEntity<APIResponse> updatePermission(Long id, PermissionRequest request);

    ResponseEntity<APIResponse> deletePermission(Long id);
    ResponseEntity<APIResponse> findPermission(String name);
}
