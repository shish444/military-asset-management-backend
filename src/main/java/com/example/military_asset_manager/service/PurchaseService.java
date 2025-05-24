package com.example.military_asset_manager.service;

import com.example.military_asset_manager.model.Asset;
import com.example.military_asset_manager.model.Purchase;
import com.example.military_asset_manager.repository.AssetRepository;
import com.example.military_asset_manager.repository.PurchaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final AssetRepository assetRepository;

    public PurchaseService(PurchaseRepository purchaseRepository, AssetRepository assetRepository) {
        this.purchaseRepository = purchaseRepository;
        this.assetRepository = assetRepository;
    }

    @Transactional
    public Purchase addPurchase(Purchase purchase) {
        // Fetch the asset to attach it to the session
        Asset asset = assetRepository.findById(purchase.getAsset().getId())
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        // Atomic balance update
        purchase.setAsset(asset);
        assetRepository.incrementBalance(asset.getId(), purchase.getQuantity());

        // Associate the managed asset with the purchase


        return purchaseRepository.save(purchase);
    }

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public List<Purchase> getPurchasesByBase(String baseName) {
        return purchaseRepository.findByBaseName(baseName);
    }
}