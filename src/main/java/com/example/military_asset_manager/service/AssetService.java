package com.example.military_asset_manager.service;

import com.example.military_asset_manager.model.Asset;
import com.example.military_asset_manager.repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {
    private final AssetRepository assetRepository;

    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Asset createAsset(Asset asset) {
        return assetRepository.save(asset);
    }

    public Asset getAssetById(Long id) {
        return assetRepository.findById(id).orElseThrow();
    }
    public void incrementAssetBalance(Long assetId, int quantity) {
        assetRepository.incrementBalance(assetId, quantity);
    }
}
