package com.unionbankng.future.futurejobservice.entities;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name="job_categories")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobCategory implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private  Long id;
    @NotNull
    private String  title;
    @Column(columnDefinition="TEXT")
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "jobCategory"
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonManagedReference(value = "category-subcategories")
    private Set<JobSubcategory> subcategories = new HashSet<>();

    @PrePersist
    public void setCreatedAt() {
        createdAt = new Date();
    }
}
