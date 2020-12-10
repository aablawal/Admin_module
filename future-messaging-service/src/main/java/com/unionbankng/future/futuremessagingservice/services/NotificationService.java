package com.unionbankng.future.futuremessagingservice.services;
import com.unionbankng.future.futuremessagingservice.entities.MessagingToken;
import com.unionbankng.future.futuremessagingservice.entities.Notification;
import com.unionbankng.future.futuremessagingservice.enums.NotificationStatus;
import com.unionbankng.future.futuremessagingservice.pojos.NotificationBody;
import com.unionbankng.future.futuremessagingservice.pojos.User;
import com.unionbankng.future.futuremessagingservice.repositories.MessagingTokenRepository;
import com.unionbankng.future.futuremessagingservice.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Service
@RequiredArgsConstructor
public class NotificationService {

    Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;
    private  final MessagingTokenRepository messagingTokenRepository;
    private final UserService userService;
    @Value("${google.sidekiq.push_notification_api_key}")
    private String token;
    //@Value("${google.sidekiq.push_notification_server_key}")
    private String serverKey="key=AAAAQHNQc1M:APA91bGLrhyQfJXUHbXTiRozIJUq6SVdkOdY2jXYITSOkTMyJEzJ7wbSlEHaAIw5ElaF_B2N-HNnqlylhxwDWtg5q-kdjkqpym85kAxntnAzxTLcyKim5-Z02UhATQ5hEyFEXNti8Hk1";
    private String baseURL="https://fcm.googleapis.com/fcm/send";
    @Autowired
    private RestTemplate rest;


    public HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apiKey",token);
        headers.set("Authorization",serverKey);
        return  headers;
    }

    public   List<Object> getAbsoluteNotifications(List<Notification> notificationList){
        List<Object> preparedNotification= new ArrayList<>();
        notificationList.forEach((notification -> {
            logger.info(notification.toString());
            Map<String,Object> notificationBase= new HashMap<>();
            notificationBase.put("notification",notification);
            notificationBase.put("source",userService.getUserById(notification.getSource()));
            notificationBase.put("destination",userService.getUserById(notification.getDestination()));
            preparedNotification.add(notificationBase);
        }));
        return  preparedNotification;
    }
    public Map<String,Object> getAbsoluteNotification(Notification notification){
        Map<String,Object> notificationBase= new HashMap<>();
        notificationBase.put("notification",notification);
        notificationBase.put("source",userService.getUserById(notification.getSource()));
        notificationBase.put("destination",userService.getUserById(notification.getDestination()));
        return  notificationBase;
    }

    public MessagingToken updateUserMID(Long userId, String umid){
        MessagingToken token=new MessagingToken();
        token.setUserId(userId);
        token.setToken(umid);
        MessagingToken currentToken=messagingTokenRepository.findTokenByUserId(userId);
        if(currentToken!=null) {
            currentToken.setToken(token.getToken());
            return messagingTokenRepository.save(currentToken);
        }
        else {
            return messagingTokenRepository.save(token);
        }
    }

    public Notification pushNotification(Long userId, NotificationBody notificationBody){
        MessagingToken sender= messagingTokenRepository.findTokenByUserId(userId);
        MessagingToken recipient= messagingTokenRepository.findTokenByUserId(notificationBody.getRecipient());
        if(recipient !=null) {
            try {

                if(notificationBody.getActionType()==null)
                    notificationBody.setActionType("REDIRECT");
                if(notificationBody.getPriority()==null)
                    notificationBody.setPriority("NORMAL");

                //prepare traditional notification
                Notification traditionalNotification = new Notification();
                traditionalNotification.setSource(userId);
                traditionalNotification.setDestination(recipient.getId());
                traditionalNotification.setMessage(notificationBody.getBody());
                traditionalNotification.setAttachment(notificationBody.getAttachment());
                traditionalNotification.setStatus(NotificationStatus.NS);
                traditionalNotification.setAction(notificationBody.getAction());
                traditionalNotification.setActionType(notificationBody.getActionType());
                traditionalNotification.setPriority(notificationBody.getPriority());
                traditionalNotification.setTopic(notificationBody.getTopic());
                traditionalNotification.setSubject(notificationBody.getSubject());
                traditionalNotification.setChannel(notificationBody.getChannel());

                if(recipient.getToken()!=null) {
                    //prepare push  notification
                    Map<String, Object> pushNotification = new HashMap<>();
                    Map<String, Object> pushBody = new HashMap<>();
                    pushBody.put("title", notificationBody.getSubject());
                    pushBody.put("body", notificationBody.getBody());
                    pushBody.put("action", notificationBody.getAction());
                    pushBody.put("actionType", notificationBody.getActionType().toString());
                    pushBody.put("priority", notificationBody.getPriority());
                    pushBody.put("topic", notificationBody.getTopic());
                    pushBody.put("icon", "./favicon.ico");
                    pushNotification.put("notification", pushBody);
                    pushNotification.put("to", recipient.getToken());

                    HttpEntity<Object> requestEntity = new HttpEntity<Object>(pushNotification, this.getHeaders());
                    ResponseEntity<String> response = rest.exchange(baseURL, HttpMethod.POST, requestEntity, String.class);
                    if (response.getStatusCode().is2xxSuccessful()) {
                       return notificationRepository.save(traditionalNotification);
                    } else {
                        logger.info("Unable to fire push notification");
                        logger.error(response.getBody());
                        return notificationRepository.save(traditionalNotification);
                    }
                }else{
                    logger.info("No permission token to fire push notification");
                    return  notificationRepository.save(traditionalNotification);
                }

            }catch (Exception e){
                logger.info("Unable to fire push notification");
                e.printStackTrace();
                return  null;
            }
        }
        else{
            logger.info("Recipient not found");
            return  null;
        }
    }
    public Notification markNotificationAsSeen(Long id){
        Notification notification=notificationRepository.findById(id).orElse(null);
        if(notification!=null)
            notification.setStatus(NotificationStatus.SE);
        return notificationRepository.save(notification);
    }

    public boolean markAllAsSeen(Long id){
        notificationRepository.updateAllAsSeen(id);
        return  true;
    }
    public boolean clearAllNotifications(Long id){
          notificationRepository.clearAll(id);
          return  true;
    }

    public Map<String,Object> findNotificationById(Long id){
        return getAbsoluteNotification(notificationRepository.findById(id).orElse(null));
    }
    public List<Object> findAllNotifications(Long id, int from, int limit){
        return  getAbsoluteNotifications(notificationRepository.findAllNotifications(id,from,limit));
    }
    public List<Object> findTopNotifications(Long id, int limit){
        return  getAbsoluteNotifications(notificationRepository.findTopNotifications(id,limit));
    }
    public List<Object> findAllNotificationsByStatus(Long id, String status){
        return  getAbsoluteNotifications(notificationRepository.findNotificationsByStatus(id,status.toUpperCase()));
    }
    public List<Object> findTopNotificationsByPriority(Long id, String priority){
        return  getAbsoluteNotifications(notificationRepository.findAllNotSeenNotificationsByPriority(id,priority.toUpperCase()));
    }
    public Map<String,Object> getNotificationBase(Long id){
        Map<String, Object> notifications=new HashMap<>();
        notifications.put("top",getAbsoluteNotifications(notificationRepository.findTopNotifications(id,20)));
        notifications.put("seen",getAbsoluteNotifications(notificationRepository.findNotificationsByStatus(id,"SE")));
        notifications.put("unseen",getAbsoluteNotifications(notificationRepository.findNotificationsByStatus(id, "NS")));
        notifications.put("all",getAbsoluteNotifications(notificationRepository.findAllNotifications(id,0,50)));
        notifications.put("important",getAbsoluteNotifications(notificationRepository.findAllNotSeenNotificationsByPriority(id,"IMPORTANT")));
        return  notifications;
    }
}
