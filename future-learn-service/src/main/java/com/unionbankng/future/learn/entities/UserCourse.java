package com.unionbankng.future.learn.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"userUUID", "courseId"})
)
public class UserCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userUUID;
    private Long courseId;
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
    public boolean equals(Object userCourse) {
        return this.id.equals(((UserCourse)userCourse).getId());

    }
}
