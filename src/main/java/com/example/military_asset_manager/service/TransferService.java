package com.example.military_asset_manager.service;

import com.example.military_asset_manager.model.Asset;
import com.example.military_asset_manager.model.Transfer;
import com.example.military_asset_manager.repository.AssetRepository;
import com.example.military_asset_manager.repository.TransferRepository;
import jakarta.transaction.Transactional;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final AssetRepository assetRepository;

    public TransferService(TransferRepository transferRepository,AssetRepository assetRepository) {
        this.transferRepository = transferRepository;
        this.assetRepository = assetRepository;
    }

    @Transactional
    public Transfer createTransfer(Long assetId, String fromBase, String toBase, int quantity) {
        // 1. Fetch the source asset (fromBase)
        Asset sourceAsset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found in source base"));

        // 2. Validate source base and quantity
        if (!sourceAsset.getCurrentBase().equals(fromBase)) {
            throw new RuntimeException("Asset is not located in the source base");
        }
        if (sourceAsset.getCurrentBalance() < quantity) {
            throw new RuntimeException("Insufficient balance in source base");
        }
        sourceAsset.setCurrentBalance(sourceAsset.getCurrentBalance() - quantity);
        try {
            assetRepository.save(sourceAsset);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new RuntimeException("Asset modified concurrently. Please retry.");
        }

        // 4. Find or create target asset (toBase)
        Asset targetAsset = assetRepository.findByNameAndTypeAndCurrentBase(
                sourceAsset.getName(),
                sourceAsset.getType(),
                toBase
        ).orElseGet(() -> {
            Asset newAsset = new Asset(
                    sourceAsset.getName(),
                    sourceAsset.getType(),
                    toBase,
                    0 // Initial balance
            );
            return assetRepository.save(newAsset);
        });
        targetAsset.setCurrentBalance(targetAsset.getCurrentBalance() + quantity);
        assetRepository.save(targetAsset);

        // 6. Create transfer record
        Transfer transfer = new Transfer();
        transfer.setAsset(sourceAsset); // Link to source asset
        transfer.setFromBase(fromBase);
        transfer.setToBase(toBase);
        transfer.setQuantity(quantity);
        transfer.setTransferTime(LocalDateTime.now());

        return transferRepository.save(transfer);
    }

        public List<Transfer> getAllTransfers() {
        return transferRepository.findAll();
    }

    public List<Transfer> getTransfersByBase(String base) {
        return transferRepository.findByFromBaseOrToBase(base);
    }
}