package com.jobtracker.jobtracker_app.model;


public enum UserRole {

    USER,
    ADMIN;

    public String toSpringSecurityRole() {
        return "ROLE_" + this.name();
    }

    public static UserRole fromSpringSecurityRole(String roleName) {
        if (roleName != null && roleName.startsWith("ROLE_")) {
            return UserRole.valueOf(roleName.substring(5));
        }
        throw new IllegalArgumentException("Invalid Spring Security role name: " + roleName);
    }

}
