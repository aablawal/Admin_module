package com.unionbankng.future.authorizationserver.interfaces;

import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.RegistrationRequest;
import org.springframework.http.ResponseEntity;


public interface AdminService {

    ResponseEntity<APIResponse> createAdmin(RegistrationRequest request);
    ResponseEntity<APIResponse>  updateUser(Long userId, RegistrationRequest request);

    ResponseEntity<APIResponse> enableUser(Long userId, String enabled);
    ResponseEntity<APIResponse> searchForAdmin(int pageNo, int pageSize, String sortBy, String sortDir, String q);

    ResponseEntity<APIResponse> getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir);
     ResponseEntity<APIResponse> userCount();
}
