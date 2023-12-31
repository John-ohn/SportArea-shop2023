package com.sportArea.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ROLE_USER(Set.of(Permission.DEVELOPERS_READ)),
    ROLE_ADMIN(Set.of(Permission.DEVELOPERS_READ, Permission.DEVELOPERS_WRITE)),
    ROLE_GUEST(Set.of(Permission.DEVELOPERS_NO_PERMISSION));

    private final Set<Permission> permissionSet;

    Role(Set<Permission> permissionSet) {
        this.permissionSet = permissionSet;
    }

    public Set<Permission> getPermissionSet() {
        return this.permissionSet;
    }

    public Set<SimpleGrantedAuthority> getAutorities() {
        return getPermissionSet()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }


}
