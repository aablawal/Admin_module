
package com.unionbankng.future.futurejobservice.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String data;
}

