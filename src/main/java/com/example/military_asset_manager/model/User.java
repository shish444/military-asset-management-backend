package com.example.military_asset_manager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @Enumerated(EnumType.STRING) // Store role as a string in DB
    private Role role;

    private String base; // Track the user's assigned base (nullable for ADMIN)

    // Constructors
    public User() {}

    public User(String username, Role role, String base) {
        this.username = username;
        this.role = role;
        this.base = base;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public String getBase() { return base; }
    public void setBase(String base) { this.base = base; }
}