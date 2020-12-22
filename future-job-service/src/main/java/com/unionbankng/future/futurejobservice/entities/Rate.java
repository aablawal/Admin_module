package com.unionbankng.future.futurejobservice.entities;
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
public class Rate {

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


    public Rate(Rate rating) {
        this.id = rating.id;
        this.userId=rating.id;
        this.rate = rating.rate;
        this.description = rating.description;
        this.feedback = rating.feedback;
        this.lastModifiedDate= new Date();
        this.createdAt = new Date();
    }

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }

    @PreUpdate
    private void setLastModifiedDate() {
        lastModifiedDate = new Date();
    }
}
