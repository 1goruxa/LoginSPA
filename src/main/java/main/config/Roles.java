package main.config;

public enum Roles {
    ADMIN ("Admin"),
    USER ("User");

    private String role;

    Roles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
