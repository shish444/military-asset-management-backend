package com.example.military_asset_manager.service;

import com.example.military_asset_manager.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class DashboardService {

    private final PurchaseRepository purchaseRepo;
    private final TransferRepository transferRepo;
    private final AssignmentRepository assignmentRepo;
    private final AssetRepository assetRepo;

    public DashboardService(
            PurchaseRepository purchaseRepo,
            TransferRepository transferRepo,
            AssignmentRepository assignmentRepo,
            AssetRepository assetRepo
    ) {
        this.purchaseRepo = purchaseRepo;
        this.transferRepo = transferRepo;
        this.assignmentRepo = assignmentRepo;
        this.assetRepo = assetRepo;
    }

    public int getOpeningBalance(String base, String type, LocalDate startDate) {
        Integer purchases = purchaseRepo.sumByBaseAndTypeBeforeDate(base, type, startDate);
        Integer transfersOut = transferRepo.sumOutByBaseAndTypeBeforeDate(base, type, startDate);
        Integer assigned = assignmentRepo.sumAssignedByBaseAndTypeBeforeDate(base, type, startDate);

        return (purchases != null ? purchases : 0)
                - (transfersOut != null ? transfersOut : 0)
                - (assigned != null ? assigned : 0);
    }

    public int getClosingBalance(String base, String type, LocalDate start, LocalDate end) {
        int opening = getOpeningBalance(base, type, start);
        int netMovement = getNetMovement(base, type, start, end);
        int assigned = getAssigned(base, type, start, end);
        int expended = getExpended(base, type, start, end);

        return opening + netMovement - assigned - expended;
    }

    public int getNetMovement(String base, String type, LocalDate start, LocalDate end) {
        Integer purchases = purchaseRepo.sumByBaseAndTypeBetweenDates(base, type, start, end);
        Integer transfersIn = transferRepo.sumInByBaseAndTypeBetweenDates(base, type, start, end);
        Integer transfersOut = transferRepo.sumOutByBaseAndTypeBetweenDates(base, type, start, end);

        return (purchases != null ? purchases : 0)
                + (transfersIn != null ? transfersIn : 0)
                - (transfersOut != null ? transfersOut : 0);
    }

    public int getAssigned(String base, String type, LocalDate start, LocalDate end) {
        Integer assigned = assignmentRepo.sumAssignedByBaseAndTypeBetweenDates(base, type, start, end);
        return assigned != null ? assigned : 0;
    }

    public int getExpended(String base, String type, LocalDate start, LocalDate end) {
        Integer expended = assignmentRepo.sumExpendedByBaseAndTypeBetweenDates(base, type, start, end);
        return expended != null ? expended : 0;
    }
    public int getTransfersOut(String base, String type, LocalDate start, LocalDate end) {
        return transferRepo.getTransfersOut(base, type, start, end);
    }

    public int getTransfersIn(String base, String type, LocalDate start, LocalDate end) {
        return transferRepo.getTransfersIn(base, type, start, end);
    }

    public int getPurchases(String base, String type, LocalDate start, LocalDate end) {
        return purchaseRepo.getPurchases(base, type, start, end);
    }
}