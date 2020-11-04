package com.unionbankng.future.learn.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Instructor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 40,unique = true)
    private String instructorUUID;
    @Column(columnDefinition = "integer default 0")
    private Integer numberOfLearners;
    @Column(columnDefinition = "integer default 0")
    private Integer numberOfCourses;

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
    public boolean equals(Object instructor) {
        return this.id.equals(((Instructor)instructor).getId());

    }
}
