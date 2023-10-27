package com.okit.authCore.models;

import com.okit.authCore.constants.UserCoreConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_user")
public class User implements UserDetails
{
    @Id
    @NotEmpty(message = UserCoreConstants.EMPTY_USERNAME_MSG)
    @Column(name = "username",nullable = false, unique = true)
    private String username;

    @NotEmpty(message = UserCoreConstants.EMPTY_PASSWORD_MSG)
    @Column(name = "password", nullable = false)
    private String password;

    @Email(message = UserCoreConstants.INVALID_EMAIL_MSG)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "username", referencedColumnName = "username"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role", referencedColumnName = "name"
            )
    )
    private Set<Role> roles = new HashSet<>();

    @Builder.Default
    @Column(name = "expired", nullable = false)
    private boolean expired = false;

    @Builder.Default
    @Column(name = "locked", nullable = false)
    private boolean locked = false;

    @Builder.Default
    @Column(name = "is_credentials_expired", nullable = false)
    private boolean isCredentialsExpired = false;

    @Builder.Default
    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return !isCredentialsExpired;
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }
}
