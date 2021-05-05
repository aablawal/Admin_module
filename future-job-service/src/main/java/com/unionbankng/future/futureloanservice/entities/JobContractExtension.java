package com.unionbankng.future.futureloanservice.entities;
import com.unionbankng.future.futureloanservice.enums.JobExtensionStatus;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name="job_contract_extension")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobContractExtension {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
    Date date;
    @NotNull
    @Enumerated(EnumType.STRING)
    JobExtensionStatus status;
    @Column(columnDefinition="TEXT")
    String reason;
    @Temporal(TemporalType.DATE)
    Date createdAt;

    public JobContractExtension(JobContractExtension request) {
        this.id = request.id;
        this.proposalId = request.proposalId;
        this.userId = request.userId;
        this.employerId = request.employerId;
        this.jobId = request.jobId;
        this.date = request.date;
        this.reason = request.reason;
        this.status=request.status;
        this.createdAt =new Date();
    }

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }

}
