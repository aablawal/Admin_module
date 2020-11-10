package com.unionbankng.future.learn.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition="TEXT")
    private String description;
    @Column(columnDefinition="TEXT")
    private String requirements;
    @Column(columnDefinition = "boolean default false")
    private Boolean isPaid;
    @Column(precision = 19, scale = 2)
    private BigDecimal price;
    @Column(nullable = false)
    private String courseTitle;
    @Column(nullable = false)
    private String shortDesc;
    @Column(columnDefinition="TEXT")
    private String outcomes;
    private String courseImg;
    @Column(columnDefinition = "boolean default false")
    private Boolean isPublished;
    @ManyToMany
    private List<Instructor> instructors = new ArrayList<>();
    private String estimatedTimeToComplete;
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
        return this.id.equals(((Course)course).getId());

    }
}
