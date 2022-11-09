package com.unionbankng.future.authorizationserver.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unionbankng.future.authorizationserver.interfaces.AdminService;
import com.unionbankng.future.authorizationserver.interfaces.PermissionService;
import com.unionbankng.future.authorizationserver.interfaces.RoleService;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.PermissionRequest;
import com.unionbankng.future.authorizationserver.pojos.RegistrationRequest;
import com.unionbankng.future.authorizationserver.pojos.RoleRequest;
import com.unionbankng.future.authorizationserver.services.ActivityLogService;
import com.unionbankng.future.authorizationserver.utils.App;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Collection;

import static com.unionbankng.future.authorizationserver.constants.AppConstants.*;

@RestController
@RequestMapping(path = "api")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    private final ActivityLogService activityLogService;

    private final RoleService roleService;

    private final PermissionService permissionService;

    private final App app;


    @PostMapping("/v1/registration/register_admin")
//    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<APIResponse> register(@Valid @RequestBody RegistrationRequest request){
        app.print("Registration Process begin");
        return adminService.createAdmin(request);
    }

    @PutMapping("/v1/update_admin/{userId}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<APIResponse> updateAdmin(@PathVariable Long userId, @Valid @RequestBody RegistrationRequest request) {
        app.print("Update Process begin");
        return adminService.updateUser(userId, request);
    }

    @PostMapping("/v1/create_role_permission")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<APIResponse> createRoleWithPermission(@RequestParam String roleName,
                                                  @RequestParam String roleFunction,
                                                  @RequestParam Collection<String> name){
        app.print("Role Creation Process begin");
       return roleService.createRoleWithNewPermission(roleName, roleFunction,name );
    }

    @PostMapping("/v1/create_role")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<APIResponse> createRole(@RequestBody RoleRequest request){
        app.print("Role Creation Process begin");
        return roleService.createRole(request);
    }

    @PostMapping("/v1/create_permission")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<APIResponse> createPermission(@RequestBody PermissionRequest request){
        app.print("Permission Creation Process begin");
        return permissionService.createPermission(request);
    }

    @PutMapping("/v1/update_role/{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<APIResponse> updateRole(@PathVariable Long id, @Valid @RequestBody RoleRequest request){
        app.print("Role update Process begin");
        return roleService.updateRole(id, request);
    }

    @PutMapping("/v1/update_permission/{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<APIResponse> updatePermission(@PathVariable Long id, @Valid @RequestBody PermissionRequest request){
        app.print("Permission update Process begin");
        return permissionService.updatePermission(id, request);
    }

    @DeleteMapping("/v1/delete_role/{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<APIResponse> deleteRole(@PathVariable Long id){
        app.print("Role deletion Process begin");
        return roleService.deleteRole(id);
    }

    @GetMapping("/v1/find_permission")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<APIResponse> findPermission(@RequestParam String name){
        app.print("Permission search Process begin");
        return permissionService.findPermission(name);
    }

    @DeleteMapping("/v1/delete_permission/{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<APIResponse> deletePermission(@PathVariable Long id){
        app.print("Permission deletion Process begin");
        return permissionService.deletePermission(id);
    }



    @PutMapping("v1/enable_admin/{userId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<APIResponse> modify(@PathVariable Long userId, @Valid @RequestParam String request) {
        app.print("Enabling Process begin");
        return adminService.enableUser(userId, request);
    }

    @GetMapping("v1/search")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<APIResponse>  searchAdmin(
            @RequestParam(value = "q", defaultValue = "k", required = false) String q,
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir){
        app.print("Searching Process begin");
        return adminService.searchForAdmin(pageNo, pageSize, sortBy, sortDir, q);
    }

    @GetMapping("v1/find_all_users")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<APIResponse>  getAllUsers(
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir){
        app.print("Searching Process begin");
        return adminService.getAllUsers(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("v1/user_count")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<APIResponse> userCount() {
        app.print("Counting Process begin");
        return adminService.userCount();
    }

    @GetMapping("v1/search_activity")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<APIResponse>  searchActivityLog(
            @RequestParam(value = "q", defaultValue = "", required = false) String q,
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir) throws JsonProcessingException {
        app.print("Searching Activity Log Process begin");
        return activityLogService.search(pageNo, pageSize, sortBy, sortDir, q);
    }
}
