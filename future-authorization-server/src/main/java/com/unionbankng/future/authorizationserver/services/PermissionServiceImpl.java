package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.Permission;
import com.unionbankng.future.authorizationserver.exceptions.ResourceNotFoundException;
import com.unionbankng.future.authorizationserver.interfaces.PermissionService;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.PermissionRequest;
import com.unionbankng.future.authorizationserver.repositories.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PermissionServiceImpl implements PermissionService {


    private final PermissionRepository permissionRepository;


    @Override
    public ResponseEntity<APIResponse> createPermission(PermissionRequest request) {
        Permission permission = permissionRepository.findByName(request.getName());
        if (permission == null) {
            permission = new Permission(request.getName());
            permission = permissionRepository.save(permission);
        }
        return ResponseEntity.ok(new APIResponse<>("Permission Created Successfully",true, permission));
    }

    @Override
    public ResponseEntity<APIResponse> updatePermission(Long id, PermissionRequest request) {
        Permission permission = permissionRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Permission", "id", id));
        permission.setName(request.getName());
        Permission updatedPermission =  permissionRepository.save(permission);

        return ResponseEntity.ok(new APIResponse<>("Permission Updated Successfully",true,updatedPermission ));
    }

    @Override
    public ResponseEntity<APIResponse> deletePermission(Long id) {
        Permission permission = permissionRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Permission", "id", id));
        permissionRepository.delete(permission);
        return ResponseEntity.ok(new APIResponse<>("Permission Deleted Successfully",true,null));
    }

    @Override
    public ResponseEntity<APIResponse> findPermission(String name) {
        Permission permission =  permissionRepository.findByName(name);

        if (permission ==  null) {
            return ResponseEntity.ok(new APIResponse<>("Permission Not Found",true,null));
        }

        return ResponseEntity.ok(new APIResponse<>("Permission Found",true,permission));
    }
}
