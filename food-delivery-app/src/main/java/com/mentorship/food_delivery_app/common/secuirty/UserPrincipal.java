package com.mentorship.food_delivery_app.common.secuirty;

import com.mentorship.food_delivery_app.role.model.Permission;
import com.mentorship.food_delivery_app.role.model.Role;
import com.mentorship.food_delivery_app.user.model.User;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
public class UserPrincipal implements UserDetails {

    private final UUID userId;

    private final String email;

    private final String password;

    private final boolean enabled;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(
            UUID userId,
            String email,
            String password,
            boolean enabled,
            Collection<? extends GrantedAuthority> authorities) {

        this.userId = userId;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {

        Set<GrantedAuthority> authorities = new HashSet<>();

        for (Role role : user.getRoles()) {

            authorities.add(
                    new SimpleGrantedAuthority("ROLE_" + role.getRoleName())
            );

            for (Permission permission : role.getPermissions()) {

                authorities.add(
                        new SimpleGrantedAuthority(
                                permission.getPermissionName()
                        )
                );
            }
        }

        return new UserPrincipal(
                user.getId(),
                user.getUserEmail(),
                user.getUserPassword(),
                user.getIsEnabled(),
                authorities
        );
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}

