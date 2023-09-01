package com.sportArea.entity;

public enum Permission {
    DEVELOPERS_READ("developers:read"),
    DEVELOPERS_WRITE("developer:write");

    private final String permission;

    Permission( String permission){
        this.permission=permission;
    }

    public String getPermission(){
        return permission;
    }


}
