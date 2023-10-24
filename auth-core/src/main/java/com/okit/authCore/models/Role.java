package com.okit.authCore.models;

import com.okit.authCore.constants.RoleCoreConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role
{
    @Id
    @NotEmpty(message = RoleCoreConstants.EMPTY_ROLE_MSG)
    @Column(name = "name",nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role", referencedColumnName = "name"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege", referencedColumnName = "name"
            )
    )
    private Set<Privilege> privileges;
}
