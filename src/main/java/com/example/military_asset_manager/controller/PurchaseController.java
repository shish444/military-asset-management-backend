package com.example.military_asset_manager.controller;

import com.example.military_asset_manager.model.Purchase;
import com.example.military_asset_manager.model.Role;
import com.example.military_asset_manager.service.PurchaseService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@CrossOrigin(origins = "*")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public List<Purchase> getAllPurchases(
            @RequestHeader("X-User-Role") Role userRole,
            @RequestHeader(value = "X-User-Base", required = false) String userBase
    ) {
        if (userRole == Role.BASE_COMMANDER) {
            if (userBase == null || userBase.isBlank()) {
                throw new RuntimeException("Base Commander must be assigned to a base");
            }
            return purchaseService.getPurchasesByBase(userBase);
        } else {
            return purchaseService.getAllPurchases();
        }
    }

    @GetMapping("/base/{baseName}")
    public List<Purchase> getPurchasesByBase(
            @PathVariable String baseName,
            @RequestHeader("X-User-Role") Role userRole,
            @RequestHeader(value = "X-User-Base", required = false) String userBase
    ) {
        if (userRole == Role.BASE_COMMANDER && !baseName.equals(userBase)) {
            throw new RuntimeException("Access denied to this base");
        }
        return purchaseService.getPurchasesByBase(baseName);
    }

    @PostMapping
    public Purchase createPurchase(
            @RequestBody Purchase purchase,
            @RequestHeader("X-User-Role") Role userRole,
            @RequestHeader(value = "X-User-Base", required = false) String userBase
    ) {
        if (userRole == Role.BASE_COMMANDER) {
            if (userBase == null || !userBase.equals(purchase.getBaseName())) {
                throw new RuntimeException("Base Commander can only create purchases for their base");
            }
        } else if (userRole != Role.ADMIN && userRole != Role.LOGISTICS) {
            throw new RuntimeException("Access denied");
        }
        return purchaseService.addPurchase(purchase);
    }
}