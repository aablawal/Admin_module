package com.unionbankng.future.learn.entities;

import com.unionbankng.future.learn.enums.LectureType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"indexNo", "courseContentId"})
)
public class Lecture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Enumerated
    private LectureType type;
    @Column(columnDefinition = "varchar(10) default '0:00'",length = 10)
    private String duration;
    private String streamingLocatorName;
    private String outputAssetName;
    @Column(nullable = false)
    private Integer indexNo;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();
    @Column(updatable = false,nullable = false)
    private String creatorUUID;
    @Column(nullable = false)
    private Long courseContentId;
    @Column(nullable = false)
    private Long courseId;
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }

    @PreUpdate
    private void setUpdatedAt() {
        updatedAt = new Date();
    }

    @Override
    public boolean equals(Object lecture) {
        return this.id.equals(((Lecture)lecture).getId());

    }
}
