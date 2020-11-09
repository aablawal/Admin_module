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
    public String supporting_files;
    @NotNull
    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    public JobType type;
    @NotNull
    @Column(nullable=false)
    public String categories;
    public String duration;
    @NotNull
    @Column(nullable=false)
    public Long budget;
    public String team;
    public String qualification;
    public String skills_required;
    public String assessment;
    public String sample_product;
    public boolean interview;
    public String timeline;
    public String payment_terms;
    @Column(name="nda_files", columnDefinition="TEXT")
    public String nda_files;
    @NotNull
    @Column(length=3, nullable=false)
    @Enumerated(EnumType.STRING)
    public JobStatus status;
    @Temporal(TemporalType.DATE)
    public Date publish_date;
    @Temporal(TemporalType.DATE)
    public Date last_modified_date;
    @Temporal(TemporalType.DATE)
    public Date createdAt;

    public Job(Job job){
         this.id=job.id;
        this.oid=job.oid;
        this.title=job.title;
        this.goal=job.goal;
        this.description=job.description;
        this.supporting_files=job.supporting_files;
        this.type=job.type;
        this.interview=job.interview;
        this.categories=job.categories;
        this.duration=job.duration;
        this.budget=job.budget;
        this.team=job.team;
        this.qualification=job.qualification;
        this.skills_required=job.skills_required;
        this.assessment=job.assessment;
        this.sample_product=job.sample_product;
        this.timeline=job.timeline;
        this.payment_terms=job.payment_terms;
        this.nda_files=job.nda_files;
        this.status=job.status;
        this.publish_date=job.publish_date;
        this.last_modified_date=job.last_modified_date;
        this.createdAt=job.createdAt;
    }

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }

    @PreUpdate
    private void lastModifiedDate() {
        last_modified_date = new Date();
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
