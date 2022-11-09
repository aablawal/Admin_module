package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.Permission;
import com.unionbankng.future.authorizationserver.entities.Role;
import com.unionbankng.future.authorizationserver.exceptions.ResourceNotFoundException;
import com.unionbankng.future.authorizationserver.interfaces.RoleService;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.RoleRequest;
import com.unionbankng.future.authorizationserver.repositories.PermissionRepository;
import com.unionbankng.future.authorizationserver.repositories.RoleRepository;
import com.unionbankng.future.authorizationserver.utils.App;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.unionbankng.future.authorizationserver.constants.KulaAdminConstants.*;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    private final App app;



    @Override
    public ResponseEntity<APIResponse> createRoleWithNewPermission(String roleName, String roleFunction, Collection<String> name) {

        Collection<Permission> permissions =  name.stream().map(Permission::new).collect(Collectors.toList());

        permissionRepository.saveAll(permissions);
        app.print(permissions);
        Role role = roleRepository.findByRoleFunction(roleFunction.toUpperCase());
        if (role == null) {
            role = new Role(roleName.toUpperCase(), roleFunction.toUpperCase());
            role.setPermissions(permissions);
            roleRepository.save(role);
        }
        return ResponseEntity.ok(new APIResponse<>("Role Created Successfully", true, role));

    }

    @Override
    public ResponseEntity<APIResponse> createRole(RoleRequest request) {
        Role role = roleRepository.findByRoleFunction(request.getRoleFunction().toUpperCase());

        if (role ==  null) {
            if (request.getRoleFunction().equalsIgnoreCase(ADMINISTRATOR)) {
                Collection<Permission> permissions = ADMINISTRATOR_PERMISSIONS.stream().map(Permission::new).collect(Collectors.toList());
               permissionRepository.saveAll(permissions);
               role = new Role(request.getRole().toUpperCase(), request.getRoleFunction().toUpperCase());
               role.setPermissions(permissions);
            } else if (request.getRoleFunction().equalsIgnoreCase(IT_CONTROL)) {
                Collection<Permission> permissions = IT_PERMISSIONS.stream().map(Permission::new).collect(Collectors.toList());
                permissionRepository.saveAll(permissions);
                role = new Role(request.getRole().toUpperCase(), request.getRoleFunction().toUpperCase());
                role.setPermissions(permissions);
            } else if (request.getRoleFunction().equalsIgnoreCase(PRODUCT_TEAM)) {
                Collection<Permission> permissions = PRODUCT_TEAM_PERMISSIONS.stream().map(Permission::new).collect(Collectors.toList());
                permissionRepository.saveAll(permissions);
                role = new Role(request.getRole().toUpperCase(), request.getRoleFunction().toUpperCase());
                role.setPermissions(permissions);
            } else if (request.getRoleFunction().equalsIgnoreCase(CPC)) {
                Collection<Permission> permissions = CPC_PERMISSIONS.stream().map(Permission::new).collect(Collectors.toList());
                permissionRepository.saveAll(permissions);
                role = new Role(request.getRole().toUpperCase(), request.getRoleFunction().toUpperCase());
                role.setPermissions(permissions);
            } else if (request.getRoleFunction().equalsIgnoreCase(OPERATIONS_SETTLEMENT_AND_RECONCILIATION)) {
                Collection<Permission> permissions = OPERATIONS_SETTLEMENT_AND_RECONCILIATION_PERMISSIONS.stream().map(Permission::new).collect(Collectors.toList());
                permissionRepository.saveAll(permissions);
                role = new Role(request.getRole().toUpperCase(), request.getRoleFunction().toUpperCase());
                role.setPermissions(permissions);
            } else if (request.getRoleFunction().equalsIgnoreCase(HELP_DESK)) {
                Collection<Permission> permissions = HELP_DESK_PERMISSIONS.stream().map(Permission::new).collect(Collectors.toList());
                permissionRepository.saveAll(permissions);
                role = new Role(request.getRole().toUpperCase(), request.getRoleFunction().toUpperCase());
                role.setPermissions(permissions);
            }
            role = new Role(request.getRole().toUpperCase(), request.getRoleFunction().toUpperCase());


            roleRepository.save(role);
        }
        return ResponseEntity.ok(new APIResponse<>("Role Created Successfully", true, role));
    }

    @Override
    public ResponseEntity<APIResponse> updateRole(Long id, RoleRequest request) {

        Role roles  = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));

//            if (request.getRole().equalsIgnoreCase(com.unionbankng.future.authorizationserver.enums.Role.ADMIN.name())) {
//                roles.setRole(com.unionbankng.future.authorizationserver.enums.Role.valueOf(request.getRole().toUpperCase()));
//                roles.setRoleFunction(request.getRoleFunction());
////                roles.setPermissions(ADMIN_PERMISSION);
//            } else {
//                roles.setRole(com.unionbankng.future.authorizationserver.enums.Role.valueOf(request.getRole().toUpperCase()));
//                roles.setRoleFunction(request.getRoleFunction());
////                roles.setPermissions(SUPER_ADMIN_PERMISSION);
//            }
//            Role updatedRole = roleRepository.save(roles);
//
            return ResponseEntity.ok(new APIResponse<>("Role Created Successfully",true, null));

    }

    @Override
    public ResponseEntity<APIResponse> deleteRole(Long id) {
            Role roles  = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
            roleRepository.delete(roles);
            return ResponseEntity.ok(new APIResponse<>("Role Deleted Successfully",true, null));
        }
}

