package com.unionbankng.future.authorizationserver.controllers;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.NotificationBody;
import com.unionbankng.future.authorizationserver.services.NotificationService;
import com.unionbankng.future.authorizationserver.services.ProfileService;
import com.unionbankng.future.authorizationserver.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class NotificationController {

    private final NotificationService notificationService;


    @PutMapping(value = "/v1/update/user/mid/{id}")
    public ResponseEntity<APIResponse<String>> updateUserMID(@RequestParam String id)  throws IOException {
        return ResponseEntity.ok().body(new APIResponse<>("Success",true,notificationService.updateUserMID(id)));
    }

    @PostMapping(value = "/v1/push/notification")
    public ResponseEntity<APIResponse<NotificationBody>> pushNotification(@Valid @RequestBody NotificationBody notificationBody)  throws IOException {
        return ResponseEntity.ok().body(new APIResponse<>("Success",true,notificationService.pushNotification(notificationBody)));
    }
}
