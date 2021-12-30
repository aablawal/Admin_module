package com.unionbankng.future.authorizationserver.entities;

import com.unionbankng.future.authorizationserver.enums.EmploymentType;
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
public class Experience implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long profileId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition="TEXT")
    private String description;

    @Enumerated(value = EnumType.STRING)
    private EmploymentType employmentType;

    @Column(nullable = false)
    private String company;

    private Boolean isCurrent;

    @Column(length = 20,nullable = false)//12/2012
    private String startDate;

    @Column(length = 20)//12/2012
    private String endDate;

    private String headline;

    private String media;

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
    public boolean equals(Object experience) {
        return this.id.equals(((Experience)experience).getId());

    }
}
