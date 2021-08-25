package com.unionbankng.future.learn.pojo;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
public class EmbeddedCourseLecture implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer indexNo;
    @Column(nullable = false)
    private Long courseId;
    @Column(columnDefinition="TEXT")
    private String description;
    @Column(columnDefinition="TEXT", nullable = false)
    private String title;
    @Column(nullable = false)
    private String url;
    private String duration;
    @Column(columnDefinition = "boolean default true")
    private Boolean isPublished;
    private String creatorUUID;
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }

    @PreUpdate
    private void setUpdatedAt() {
        updatedAt = new Date();
    }

    @Override
    public boolean equals(Object course) {
        return this.id.equals(((EmbeddedCourseLecture)course).getId());

    }
}
