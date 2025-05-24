package com.example.military_asset_manager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "assets",uniqueConstraints = @UniqueConstraint(
        columnNames = {"name", "type", "currentBase"}
))
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( columnDefinition = "VARCHAR(255) ")
    private String name;
    @Column(nullable = false, columnDefinition = "VARCHAR(255) ")
    private String type;
    private String currentBase;   // Track base location
    @Column(nullable = false, columnDefinition = "integer default 0")
    private int currentBalance;   // Track quantity
    @Version
    private Long version;

    // Constructors
    public Asset() {}

    public Asset(String name, String type, String currentBase, int currentBalance) {
        this.name = name;
        this.type = type;
        this.currentBase = currentBase;
        this.currentBalance = currentBalance;
    }

    // Getters & Setters (include new fields)
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getCurrentBase() { return currentBase; }
    public void setCurrentBase(String currentBase) { this.currentBase = currentBase; }
    public int getCurrentBalance() { return currentBalance; }
    public void setCurrentBalance(int currentBalance) { this.currentBalance = currentBalance; }
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    public void setId(Long id) {
        this.id=id;
    }
}