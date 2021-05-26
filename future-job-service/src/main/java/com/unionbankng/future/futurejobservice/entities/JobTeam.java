package com.unionbankng.future.futurejobservice.entities;
import com.unionbankng.future.futurejobservice.enums.JobStatus;
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


    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }
}
