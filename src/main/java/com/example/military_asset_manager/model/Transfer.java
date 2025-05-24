package com.example.military_asset_manager.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Column(name = "from_base", nullable = false)
    private String fromBase;

    @Column(name = "to_base", nullable = false)
    private String toBase;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "transfer_time", nullable = false)
    private LocalDateTime transferTime;

    // Constructors
    public Transfer() {}

    public Transfer(Asset asset, String fromBase, String toBase, int quantity, LocalDateTime transferTime) {
        this.asset = asset;
        this.fromBase = fromBase;
        this.toBase = toBase;
        this.quantity = quantity;
        this.transferTime = transferTime;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public Asset getAsset() { return asset; }
    public void setAsset(Asset asset) { this.asset = asset; }
    public String getFromBase() { return fromBase; }
    public void setFromBase(String fromBase) { this.fromBase = fromBase; }
    public String getToBase() { return toBase; }
    public void setToBase(String toBase) { this.toBase = toBase; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public LocalDateTime getTransferTime() { return transferTime; }
    public void setTransferTime(LocalDateTime transferTime) { this.transferTime = transferTime; }
}