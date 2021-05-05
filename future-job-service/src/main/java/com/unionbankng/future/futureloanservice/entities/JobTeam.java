package com.unionbankng.future.futureloanservice.entities;
import com.unionbankng.future.futureloanservice.enums.JobStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name="job_teams")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobTeam {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;
    @NotNull
    Long jobId;
    @NotNull
    String invitationId;
    @Column(columnDefinition="TEXT")
    String selectedTeam;
    @NotNull
    JobStatus status;
    @Temporal(TemporalType.DATE)
    Date createdAt;

    public JobTeam(JobTeam jobTeam) {
        this.id = jobTeam.id;
        this.jobId = jobTeam.jobId;
        this.invitationId = jobTeam.invitationId;
        this.selectedTeam = jobTeam.selectedTeam;
        this.status=jobTeam.status;
        this.createdAt = new Date();
    }

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }
}
