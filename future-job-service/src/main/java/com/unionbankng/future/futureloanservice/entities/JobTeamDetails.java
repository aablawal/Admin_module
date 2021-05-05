package com.unionbankng.future.futureloanservice.entities;
import com.unionbankng.future.futureloanservice.enums.JobTeamStatus;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name="job_team_details")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobTeamDetails {

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
    String fullName;
    String img;
    String email;
    @NotNull
    Long jobId;
    Long amount;
    Long percentage;
    @Temporal(TemporalType.DATE)
    Date startDate;
    @Temporal(TemporalType.DATE)
    Date endDate;
    @Column(columnDefinition="TEXT")
    String description;
    @NotNull
    @Enumerated(EnumType.STRING)
    JobTeamStatus status;
    @NotNull
    @Temporal(TemporalType.DATE)
    Date createdAt;

    public JobTeamDetails(JobTeamDetails request) {
        this.id = request.id;
        this.proposalId = request.proposalId;
        this.userId = request.userId;
        this.fullName=request.fullName;
        this.email=request.email;
        this.img=request.img;
        this.employerId = request.employerId;
        this.jobId = request.jobId;
        this.amount=request.getAmount();
        this.description=request.description;
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

