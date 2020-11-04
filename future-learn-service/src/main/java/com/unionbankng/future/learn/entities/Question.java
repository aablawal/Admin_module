package com.unionbankng.future.learn.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"index", "lectureId"})
)
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition="TEXT")
    private String questionText;
    @Column(nullable = false)
    private Integer index;
    @Column(nullable = false)
    private Long lectureId;
    @OneToMany(cascade = CascadeType.ALL)
    private List<QuestionOption> options;
    @Column(nullable = false)
    private Integer answerIndex;
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
    public boolean equals(Object question) {
        return this.id.equals(((Question)question).getId());

    }
}
