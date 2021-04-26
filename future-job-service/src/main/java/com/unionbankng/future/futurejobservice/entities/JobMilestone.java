package com.unionbankng.future.futurejobservice.entities;
import com.unionbankng.future.futurejobservice.enums.JobMilestoneStatus;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Table(name="job_milestones")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobMilestone {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;
    @NotNull
    Long proposalId;
    @NotNull
    Long userId;
    @NotNull
    Long employerId;
    @NotNull
    Long jobId;
    @NotNull
    Long amount;
    @Temporal(TemporalType.DATE)
    Date startDate;
    @NotNull
    @Temporal(TemporalType.DATE)
    Date endDate;
    @NotNull
    @Column(columnDefinition="TEXT")
    String title;
    public String supportingFiles;
    @NotNull
    @Column(columnDefinition="TEXT")
    String description;
    @NotNull
    @Enumerated(EnumType.STRING)
    JobMilestoneStatus status;
    @Temporal(TemporalType.DATE)
    Date createdAt;

    public JobMilestone(JobMilestone request) {
        this.id = request.id;
        this.proposalId = request.proposalId;
        this.userId = request.userId;
        this.employerId = request.employerId;
        this.jobId = request.jobId;
        this.title = request.title;
        this.amount=request.getAmount();
        this.description=request.description;
        this.supportingFiles=request.supportingFiles;
        this.startDate=request.startDate;
        this.endDate=request.endDate;
        this.status=request.status;
        this.createdAt = new Date();
    }

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }

}

