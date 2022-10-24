package com.unionbankng.future.authorizationserver.entities;

import com.unionbankng.future.authorizationserver.enums.ProfileType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long userId;

    private String profilePhoto;

    private String coverPhoto;

    @Enumerated(EnumType.STRING)
    private ProfileType profileType;

    private Boolean isEmployer = false;

    private Boolean isFreelancer = false;

    @Column(precision = 19, scale = 2)
    private BigDecimal pricePerHour;

    private String jobTitle;

    @Column(columnDefinition="TEXT")
    private  String bio;

    @OneToMany
    private Set<ProfileSkill> skills = new HashSet<>();
    private Integer percentageComplete;

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
    public boolean equals(Object profile) {
        return this.id.equals(((Profile)profile).getId());

    }
    public void incrementPercentageComplete(int percentage){
//        this.percentageComplete = percentageComplete + percentage;
        System.out.println("PERCENTAGE INCREMENTED BY " + percentage + "GIVES" + percentageComplete);
    }

    public void decrementPercentageComplete(int percentage){
//        this.percentageComplete = percentageComplete - percentage;
        System.out.println("PERCENTAGE DECREMENTED BY " + percentage + "GIVES" + percentageComplete);
    }

    public int getPercentageComplete(){
//        if(this.percentageComplete == null){
//            return 0;
//        }
//        else{
//            return this.percentageComplete;
//        }
        System.out.println("PERCENTAGE DECREMENTED BY "  + "GIVES" + percentageComplete);
        return 0;
    }

}
