package com.unionbankng.future.futureutilityservice.entities;

import com.sun.istack.NotNull;
import com.unionbankng.future.futureutilityservice.enums.LoggingOwner;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Table(name="activity_logs")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLog {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    LoggingOwner owner;
    @Column(columnDefinition="TEXT")
    String requestObject;
    @Column(columnDefinition="TEXT")
    String responseObject;
    String userId;
    String username;
    String device;
    String ipAddress;
    String description;
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }
}
