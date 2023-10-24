package com.okit.authCore.models;

import com.okit.authCore.constants.PrivilegeCoreConstants;
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
@Table(name = "privilege")
public class Privilege
{
    @Id
    @NotEmpty(message = PrivilegeCoreConstants.EMPTY_PRIVILEGE_MSG)
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Set<Role> roles;
}
