package com.example.military_asset_manager.controller;

import com.example.military_asset_manager.model.Asset;
import com.example.military_asset_manager.service.AssetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/assets")
@CrossOrigin(origins = "http://localhost:3000")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping
    public List<Asset> getAllAssets() {
        return assetService.getAllAssets();
    }

    @PostMapping
    public Asset createAsset(@RequestBody Asset asset) {
        return assetService.createAsset(asset);
    }
}
