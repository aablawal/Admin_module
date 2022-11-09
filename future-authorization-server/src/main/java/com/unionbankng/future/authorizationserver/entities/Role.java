package com.unionbankng.future.authorizationserver.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String role;

    private String roleFunction;

//    @ManyToMany(mappedBy = "roles")
//    private Collection<User> users;


    public Role(String role, String roleFunction) {
        this.role = role;
        this.roleFunction = roleFunction;
    }

    @ManyToMany
    @JoinTable(name = "roles_permissions",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "permission_id", referencedColumnName = "id"))
    private Collection<Permission> permissions;

}
