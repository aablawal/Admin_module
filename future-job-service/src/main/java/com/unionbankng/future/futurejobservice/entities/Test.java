
package com.unionbankng.future.futurejobservice.entities;
import javax.persistence.*;
import lombok.*;

import java.util.Date;


@Data
@Entity
@Getter
@Setter
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String data;
    private String remark;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    private void setCreatedAt() {
        this.createdAt = new Date();
    }

}

