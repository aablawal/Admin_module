package com.unionbankng.future.futurejobservice.entities;
import com.unionbankng.future.futurejobservice.enums.JobStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Table(name="job_teams")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobTeam implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long jobId;
    @NotNull
    private String invitationId;
    @Column(columnDefinition="TEXT")
    private   String selectedTeam;
    @NotNull
    private  JobStatus status;
    @Temporal(TemporalType.DATE)
    private   Date createdAt;


    @PrePersist
    public void setCreatedAt() {
        createdAt = new Date();
    }
}
