package com.example.military_asset_manager.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Column(nullable = false)
    private String base;

    @Column(nullable = false)
    private String assignedTo;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private LocalDateTime assignedAt;

    @Column(nullable = false)
    private boolean isExpended = false;

    private LocalDateTime expendedAt;

    public Assignment() {}

    public Assignment(Asset asset, String base, String assignedTo, int quantity, LocalDateTime assignedAt) {
        this.asset = asset;
        this.base = base;
        this.assignedTo = assignedTo;
        this.quantity = quantity;
        this.assignedAt = assignedAt;
    }

    // Getters and setters...

    public Long getId() {
        return id;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public boolean isExpended() {
        return isExpended;
    }

    public void setExpended(boolean expended) {
        isExpended = expended;
    }

    public LocalDateTime getExpendedAt() {
        return expendedAt;
    }

    public void setExpendedAt(LocalDateTime expendedAt) {
        this.expendedAt = expendedAt;
    }
}
