package com.unionbankng.future.futurejobservice.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Table(name="job_subcategories")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobSubcategory implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private  Long id;
    @NotNull
    private String  title;
    @Column(columnDefinition="TEXT")
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_category_id")
    @JsonBackReference(value = "category-subcategories")
    private JobCategory jobCategory;

    @PrePersist
    public void setCreatedAt() {
        createdAt = new Date();
    }
}
