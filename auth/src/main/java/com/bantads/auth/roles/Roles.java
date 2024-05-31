package com.bantads.auth.roles;

public enum Roles {
    CLIENT("Client"),
    GERENTE("Gerente"),
    ADMIN("Admin");

    private final String role;

    Roles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return role;
    }
}
