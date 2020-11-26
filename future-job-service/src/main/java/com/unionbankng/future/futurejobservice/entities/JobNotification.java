package com.unionbankng.future.futurejobservice.entities;
import com.unionbankng.future.futurejobservice.enums.JobNotificationStatus;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name="job_notifications")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobNotification {
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
    @NotNull
    @Enumerated(EnumType.STRING)
    JobNotificationStatus status;
    String attachment;
    @Temporal(TemporalType.DATE)
    Date createdAt;

    public JobNotification(JobNotification notification) {
        this.id = notification.id;
        this.source = notification.source;
        this.destination = notification.destination;
        this.message = notification.message;
        this.status=notification.status;
        this.attachment = notification.attachment;
        this.createdAt = notification.createdAt;
    }

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }
}
