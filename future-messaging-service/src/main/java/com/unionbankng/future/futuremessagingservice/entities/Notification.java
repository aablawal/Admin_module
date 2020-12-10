package com.unionbankng.future.futuremessagingservice.entities;
import com.unionbankng.future.futuremessagingservice.enums.NotificationStatus;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name="notifications")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;
    @NotNull
    Long source;
    @NotNull
    Long destination;
    @NotNull
    @Column(columnDefinition="TEXT")
    String message;
    @Column(columnDefinition="TEXT")
    String subject;
    @NotNull
    @Enumerated(EnumType.STRING)
    NotificationStatus status;
    @Column(columnDefinition="TEXT")
    String attachment;
    @Column(length = 50)
    String channel;
    String action;
    @Column(length = 50)
    String actionType;
    @Column(length = 50)
    String priority;
    @Column(length = 50)
    String topic;
    @Temporal(TemporalType.DATE)
    Date createdAt;

    public Notification(Notification notification) {
        this.id = notification.id;
        this.source = notification.source;
        this.destination = notification.destination;
        this.message = notification.message;
        this.status=notification.status;
        this.action=notification.action;
        this.actionType=notification.actionType;
        this.topic=notification.topic;
        this.attachment = notification.attachment;
        this.createdAt = notification.createdAt;
    }

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }
}