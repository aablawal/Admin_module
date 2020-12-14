package com.unionbankng.future.futurejobservice.entities;
import com.unionbankng.future.futurejobservice.enums.JobStatus;
import com.unionbankng.future.futurejobservice.enums.JobType;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Table(name="jobs")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;
    @NotNull
    @Column(nullable=false)
    public Long oid;
    @NotNull
    @Column(columnDefinition ="TEXT", nullable=false)
    public String title;
    @NotNull
    @Column(columnDefinition ="TEXT", nullable=false)
    public String goal;
    @NotNull
    @Column(columnDefinition ="TEXT", nullable=false)
    public String description;
    public String supportingFiles;
    @NotNull
    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    public JobType type;
    @NotNull
    @Column(nullable=false)
    public String categories;
    public String invitationId;
    public String duration;
    @NotNull
    @Column(nullable=false)
    public Long budget;
    public String team;
    public String qualification;
    public String skillsRequired;
    public String assessment;
    public String sampleProduct;
    public boolean interview;
    public String timeline;
    public String paymentTerms;
    public String teamExpertiseLevel;
    public Long teamSize;
    @Column(name="nda_files", columnDefinition="TEXT")
    public String ndaFiles;
    @NotNull
    @Column(length=3, nullable=false)
    @Enumerated(EnumType.STRING)
    public JobStatus status;
    @Temporal(TemporalType.DATE)
    public Date publishDate;
    @Temporal(TemporalType.DATE)
    public Date lastModifiedDate;
    @Temporal(TemporalType.DATE)
    public Date createdAt;

    public Job(Job job){
        this.id=job.id;
        this.oid=job.oid;
        this.title=job.title;
        this.goal=job.goal;
        this.description=job.description;
        this.supportingFiles=job.supportingFiles;
        this.type=job.type;
        this.interview=job.interview;
        this.categories=job.categories;
        this.duration=job.duration;
        this.budget=job.budget;
        this.team=job.team;
        this.teamSize=job.teamSize;
        this.teamExpertiseLevel=job.teamExpertiseLevel;
        this.qualification=job.qualification;
        this.skillsRequired=job.skillsRequired;
        this.assessment=job.assessment;
        this.sampleProduct=job.sampleProduct;
        this.timeline=job.timeline;
        this.invitationId=job.invitationId;
        this.paymentTerms=job.paymentTerms;
        this.ndaFiles=job.ndaFiles;
        this.status=job.status;
        this.publishDate=job.publishDate;
        this.lastModifiedDate=new Date();
        this.createdAt= new Date();
    }

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }

    @PreUpdate
    private void lastModifiedDate() {
        lastModifiedDate = new Date();
    }

    @Override
    public boolean equals(Object job) {
        return this.id.equals(((Job)job).getId());
    }

    @Override
    public String toString() {
        return this.title;
    }
}
