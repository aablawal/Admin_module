package com.unionbankng.future.futurejobservice.entities;
import com.unionbankng.future.futurejobservice.enums.JobStatus;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Table(name="job_contract_extension")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobContractExtension implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private  Long proposalId;
    @NotNull
    private  Long userId;
    @NotNull
    private  Long employerId;
    @NotNull
    private  Long jobId;
    @NotNull
    private  String contractReference;
    @NotNull
    private  Date date;
    @NotNull
    @Enumerated(EnumType.STRING)
    private  JobStatus status;
    @Column(columnDefinition="TEXT")
    private  String reason;
    @Temporal(TemporalType.DATE)
    private  Date createdAt;

    @PrePersist
    public void setCreatedAt() {
        createdAt = new Date();
    }

}
