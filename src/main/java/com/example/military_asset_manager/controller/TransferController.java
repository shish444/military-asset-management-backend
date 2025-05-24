package com.example.military_asset_manager.controller;

import com.example.military_asset_manager.model.Transfer;
import com.example.military_asset_manager.model.Role;
import com.example.military_asset_manager.service.TransferService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transfers")
@CrossOrigin(origins = "*")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    // Get all transfers (filter by base for Base Commanders)
    @GetMapping
    public List<Transfer> getAllTransfers(
            @RequestHeader("X-User-Role") Role userRole,
            @RequestHeader(value = "X-User-Base", required = false) String userBase,
            @RequestParam(required = false) String base
    ) {
        if (userRole == Role.BASE_COMMANDER) {
            if (userBase == null || userBase.isBlank()) {
                throw new RuntimeException("Base Commander must be assigned to a base");
            }
            return transferService.getTransfersByBase(userBase);
        } else {
            // Admins/Logistics: Return all transfers (optionally filtered by base)
            return base != null ? transferService.getTransfersByBase(base) : transferService.getAllTransfers();
        }
    }

    // Create a transfer (only Logistics and Admin)
    @PostMapping
    public Transfer createTransfer(
            @RequestBody Map<String, Object> payload,
            @RequestHeader("X-User-Role") Role userRole,
            @RequestHeader(value = "X-User-Base", required = false) String userBase
    ) {
        // Only Logistics and Admin can create transfers
        if (userRole != Role.LOGISTICS && userRole != Role.ADMIN) {
            throw new RuntimeException("Access denied");
        }

        // Extract transfer details
        Long assetId = Long.valueOf(payload.get("assetId").toString());
        String fromBase = payload.get("fromBase").toString();
        String toBase = payload.get("toBase").toString();
        int quantity = Integer.parseInt(payload.get("quantity").toString());

        // Logistics can only transfer from their assigned base
        if (userRole == Role.LOGISTICS && !fromBase.equals(userBase)) {
            throw new RuntimeException("Logistics officers can only transfer from their base");
        }

        return transferService.createTransfer(assetId, fromBase, toBase, quantity);
    }
}