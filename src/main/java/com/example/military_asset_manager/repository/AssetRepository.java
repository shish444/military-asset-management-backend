package com.example.military_asset_manager.repository;

import com.example.military_asset_manager.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    // Sum current balances for closing balance calculation
    @Query("SELECT COALESCE(SUM(a.currentBalance), 0) FROM Asset a " +
            "WHERE a.currentBase = :base AND a.type = :type")
    Integer sumByBaseAndType(
            @Param("base") String base,
            @Param("type") String type
    );
    @Modifying
    @Query("UPDATE Asset a SET a.currentBalance = a.currentBalance + :quantity WHERE a.id = :id")
    void incrementBalance(@Param("id") Long id, @Param("quantity") int quantity);
    @Query("SELECT a FROM Asset a " +
            "WHERE LOWER(a.name) = LOWER(:name) " +
            "AND LOWER(a.type) = LOWER(:type) " +
            "AND LOWER(a.currentBase) = LOWER(:base)")
    Optional<Asset> findByNameAndTypeAndCurrentBase(
            @Param("name") String name,
            @Param("type") String type,
            @Param("base") String base
    );
}