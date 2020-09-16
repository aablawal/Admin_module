package com.unionbankng.future.authorizationserver.entities;

import com.unionbankng.future.authorizationserver.enums.TagType;
import com.unionbankng.future.authorizationserver.enums.UserType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length=50, nullable = false)
    private String name;
    @Enumerated(value = EnumType.STRING)
    private TagType type;


    @Override
    public boolean equals(Object tag) {
        return this.id.equals(((Tag)tag).getId()) && this.type.equals(((Tag)tag).getType());

    }

    @Override
    public String toString() {
        return this.name;
    }
}
