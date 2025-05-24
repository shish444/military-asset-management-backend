package com.example.military_asset_manager.controller;

import com.example.military_asset_manager.model.Role;
import com.example.military_asset_manager.service.DashboardService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;


    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }


    @GetMapping
    public DashboardMetrics getDashboardMetrics(
            @RequestHeader("X-User-Role") Role userRole,
            @RequestHeader(value = "X-User-Base", required = false) String userBase,
            @RequestParam(required = false) String base,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        // RBAC enforcement
        if (userRole == Role.BASE_COMMANDER) {
            if (userBase == null || userBase.isBlank()) {
                throw new RuntimeException("Base Commander must be assigned to a base");
            }
            base = userBase;
        }

        // Default date range: last 30 days
        if (startDate == null) startDate = LocalDate.now().minusDays(30);
        if (endDate == null) endDate = LocalDate.now();

        return new DashboardMetrics(
                dashboardService.getOpeningBalance(base, type, startDate),
                dashboardService.getClosingBalance(base, type, startDate, endDate),
                dashboardService.getNetMovement(base, type, startDate, endDate),
                dashboardService.getAssigned(base, type, startDate, endDate),
                dashboardService.getExpended(base, type, startDate, endDate),
                dashboardService.getPurchases(base, type, startDate, endDate),
                dashboardService.getTransfersIn(base, type, startDate, endDate),
                dashboardService.getTransfersOut(base, type, startDate, endDate)
        );
    }

    // Enhanced DTO with breakdown
    private static class DashboardMetrics {
        public int openingBalance;
        public int closingBalance;
        public int netMovement;
        public int assigned;
        public int expended;
        public int purchases;
        public int transfersIn;
        public int transfersOut;

        public DashboardMetrics(int openingBalance, int closingBalance,
                                int netMovement, int assigned, int expended,
                                int purchases, int transfersIn, int transfersOut) {
            this.openingBalance = openingBalance;
            this.closingBalance = closingBalance;
            this.netMovement = netMovement;
            this.assigned = assigned;
            this.expended = expended;
            this.purchases = purchases;
            this.transfersIn = transfersIn;
            this.transfersOut = transfersOut;
        }
    }
}