package com.unionbankng.future.learn.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"courseId", "userUUID"})
)
public class UserCourseProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long courseId;
    @Column(nullable = false)
    private String userUUID;
    @ManyToMany
    private List<Lecture> lecturesTaken = new ArrayList<>();
    @Column(nullable = false)
    private Integer currentLectureId;
    private Double progressPercentage = 0.00;

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
    public boolean equals(Object userCourseProgress) {
        return this.id.equals(((UserCourseProgress)userCourseProgress).getId());

    }
}
