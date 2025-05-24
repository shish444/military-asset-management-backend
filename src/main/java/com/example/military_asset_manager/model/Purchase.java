package com.example.military_asset_manager.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Map;

@Entity
@Table(name = "purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private String baseName;
    private int quantity;
    private LocalDate purchaseDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "asset_id",nullable = false)
    private Asset asset;

    // Constructors
    public Purchase() {
        this.purchaseDate = LocalDate.now();
    }


    @JsonCreator
    public Purchase(@JsonProperty("baseName") String baseName,
                    @JsonProperty("quantity") int quantity,
                    @JsonProperty("asset") Map<String, Long> asset // Accept { "id": 1 }
    ){
        this.baseName = baseName;
        this.quantity = quantity;
        this.asset = new Asset();
        this.asset.setId(asset.get("id"));
        this.purchaseDate = LocalDate.now();

    }

    // Getters & Setters
    public Long getId() { return id; }
    public String getBaseName() { return baseName; }
    public void setBaseName(String baseName) { this.baseName = baseName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public LocalDate getPurchaseDate() { return purchaseDate; }
    public Asset getAsset() { return asset; }
    public void setAsset(Asset asset) { this.asset = asset; }


}