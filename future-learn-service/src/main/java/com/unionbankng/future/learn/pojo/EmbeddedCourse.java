package com.unionbankng.future.learn.pojo;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class EmbeddedCourse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long category;
    @Column(columnDefinition="TEXT")
    private String description;
    @Column(columnDefinition="TEXT")
    private String requirements;
    private String paymentOption;
    @Column(columnDefinition = "boolean default false")
    private Boolean isPaid;
    @Column(precision = 19, scale = 2)
    private BigDecimal price;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false,columnDefinition="TEXT")
    private String fullDescription;
    private String courseImg;
    @Column(columnDefinition = "boolean default false")
    private Boolean isPublished;
    private String instructor;
    private String duration;
    @Column(nullable = false)
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
        return this.id.equals(((EmbeddedCourse)course).getId());
    }
}
