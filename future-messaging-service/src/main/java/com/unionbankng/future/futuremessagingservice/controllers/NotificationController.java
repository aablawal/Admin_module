package com.unionbankng.future.futuremessagingservice.controllers;
import com.unionbankng.future.futuremessagingservice.entities.Notification;
import com.unionbankng.future.futuremessagingservice.pojos.APIResponse;
import com.unionbankng.future.futuremessagingservice.pojos.NotificationBody;
import com.unionbankng.future.futuremessagingservice.services.NotificationService;
import com.unionbankng.future.futuremessagingservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @PostMapping(value = "/v1/push/notification/{id}")
    public ResponseEntity<APIResponse<ResponseEntity<String>>> pushNotification(@Valid @PathVariable Long id, @Valid @RequestBody NotificationBody notificationBody)  {
        return ResponseEntity.ok().body(new APIResponse<>("Success",true,notificationService.pushNotification(id,notificationBody)));
    }
    @PutMapping(value = "/v1/notification/mark/seen/{id}")
    public ResponseEntity<APIResponse<Notification>> markAsSeen(@Valid @PathVariable Long id){
        return ResponseEntity.ok().body(new APIResponse<>("Success",true,notificationService.markNotificationAsSeen(id)));
    }
    @PutMapping(value = "/v1/notification/mark/all/seen/{id}")
    public ResponseEntity<APIResponse<Boolean>> markAllAsSeen(@Valid @PathVariable Long id){
        return ResponseEntity.ok().body(new APIResponse<>("Success",true,notificationService.markAllAsSeen(id)));
    }
    @PutMapping(value = "/v1/notification/clear/all/{id}")
    public ResponseEntity<APIResponse<Boolean>> clearAll(@Valid @PathVariable Long id){
        return ResponseEntity.ok().body(new APIResponse<>("Success",true,notificationService.clearAllNotifications(id)));
    }
    @GetMapping(value = "/v1/notifications/base/{id}")
    public ResponseEntity<APIResponse<Map<String, Object>>> getNotificationBase(@Valid @PathVariable Long id){
        return ResponseEntity.ok().body(new APIResponse<>("Success",true,notificationService.getNotificationBase(id)));
    }

    @GetMapping(value = "/v1/notifications/list/{id}")
    public ResponseEntity<APIResponse<List<Object>>> getAllNotifications(@Valid @PathVariable Long id, @RequestParam int limit, @RequestParam int from){
        return ResponseEntity.ok().body(new APIResponse<>("Success",true,notificationService.findAllNotifications(id,from,limit)));
    }

    @GetMapping(value = "/v1/notifications/top/{id}")
    public ResponseEntity<APIResponse<List<Object>>> getTopNotifications(@Valid @PathVariable Long id,  @RequestParam int limit){
        return ResponseEntity.ok().body(new APIResponse<>("Success",true,notificationService.findTopNotifications(id,limit)));
    }

    @GetMapping(value = "/v1/notifications/status/{id}")
    public ResponseEntity<APIResponse<List<Object>>> getAllNotificationsByStatus(@Valid @PathVariable Long id, @RequestParam String type){
        return ResponseEntity.ok().body(new APIResponse<>("Success",true,notificationService.findAllNotificationsByStatus(id,type)));
    }

    @GetMapping(value = "/v1/notifications/priority/{id}")
    public ResponseEntity<APIResponse<List<Object>>> getAllNotificationsByPriority(@Valid @PathVariable Long id, @RequestParam String type){
        return ResponseEntity.ok().body(new APIResponse<>("Success",true,notificationService.findTopNotificationsByPriority(id,type)));
    }

    @GetMapping(value = "/v1/test")
    public ResponseEntity<APIResponse<String>> test(){
        logger.info("Hello from messaging service");
        logger.info(userService.getUserById(Long.valueOf(1)).toString());
        return ResponseEntity.ok().body(new APIResponse<>("Success",true,"It worked finnaly"));
    }

}
