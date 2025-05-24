package com.example.military_asset_manager.repository;

import com.example.military_asset_manager.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    // Find all purchases for a specific base
    List<Purchase> findByBaseName(String baseName);

    // Sum purchases for dashboard calculations
    @Query("SELECT COALESCE(SUM(p.quantity), 0) FROM Purchase p " +
            "WHERE LOWER(p.baseName) = LOWER(:base) AND LOWER(p.asset.type) = LOWER(:type) AND (p.purchaseDate <= :date OR p.purchaseDate IS NULL)")
    Integer sumByBaseAndTypeBeforeDate(
            @Param("base") String base,
            @Param("type") String type,
            @Param("date") LocalDate date
    );

    @Query("SELECT COALESCE(SUM(p.quantity), 0) FROM Purchase p " +
            "WHERE LOWER(p.baseName) = LOWER(:base) AND LOWER(p.asset.type) = LOWER(:type) AND p.purchaseDate BETWEEN :start AND :end")
    Integer sumByBaseAndTypeBetweenDates(
            @Param("base") String base,
            @Param("type") String type,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
    @Query("SELECT COALESCE(SUM(p.quantity), 0) FROM Purchase p " +
            "WHERE LOWER(p.baseName) = LOWER(:base) AND LOWER(p.asset.type) = LOWER(:type)" +
            "AND p.purchaseDate BETWEEN :start AND :end")
    int getPurchases(@Param("base") String base,
                     @Param("type") String type,
                     @Param("start") LocalDate start,
                     @Param("end") LocalDate end);
}