package com.unionbankng.future.futureloanservice.entities;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name="job_freelancer_rating")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExampleEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;
    @NotNull
    Long userId;
    @NotNull
    Long rate;
    @NotNull
    String description;
    @Column(columnDefinition="TEXT")
    String feedback;
    @Temporal(TemporalType.DATE)
    Date lastModifiedDate;
    @Temporal(TemporalType.DATE)
    Date createdAt;

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }

    @PreUpdate
    private void setLastModifiedDate() {
        lastModifiedDate = new Date();
    }
}
