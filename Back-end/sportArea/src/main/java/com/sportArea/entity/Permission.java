package com.sportArea.entity;

public enum Permission {
    DEVELOPERS_READ("developers:read"),
    DEVELOPERS_WRITE("developer:write"),
    DEVELOPERS_NO_PERMISSION("developer:no_permission");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }


}
