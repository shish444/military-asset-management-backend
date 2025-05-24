package com.example.military_asset_manager.repository;

import com.example.military_asset_manager.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    // Sum outgoing transfers before a date (Native SQL)
    @Query(value = """
        SELECT COALESCE(SUM(t.quantity), 0) 
        FROM transfers t 
        JOIN assets a ON t.asset_id = a.id 
        WHERE LOWER(t.from_base) = LOWER(:base)
        AND LOWER(a.type) = LOWER(:type) 
        AND t.transfer_time <= CAST(:date AS TIMESTAMP) + INTERVAL '1 DAY' - INTERVAL '1 SECOND'
    """, nativeQuery = true)
    Integer sumOutByBaseAndTypeBeforeDate(
            @Param("base") String base,
            @Param("type") String type,
            @Param("date") LocalDate date
    );

    // Sum incoming transfers within date range
    @Query(value = """
        SELECT COALESCE(SUM(t.quantity), 0) 
        FROM transfers t 
        JOIN assets a ON t.asset_id = a.id 
        WHERE LOWER(t.to_base) = LOWER(:base) 
        AND a.type = :type 
        AND t.transfer_time BETWEEN CAST(:start AS TIMESTAMP) AND CAST(:end AS TIMESTAMP)
        """, nativeQuery = true)
    Integer sumInByBaseAndTypeBetweenDates(
            @Param("base") String base,
            @Param("type") String type,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    // Sum outgoing transfers within date range
    @Query(value = """
        SELECT COALESCE(SUM(t.quantity), 0) 
        FROM transfers t 
        JOIN assets a ON t.asset_id = a.id 
        WHERE LOWER(t.from_base) = LOWER(:base) 
        AND LOWER(a.type) = LOWER(:type) 
        AND t.transfer_time BETWEEN CAST(:start AS TIMESTAMP) AND CAST(:end AS TIMESTAMP)
        """, nativeQuery = true)
    Integer sumOutByBaseAndTypeBetweenDates(
            @Param("base") String base,
            @Param("type") String type,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
    @Query("SELECT t FROM Transfer t WHERE t.fromBase = :base OR t.toBase = :base")
    List<Transfer> findByFromBaseOrToBase(@Param("base") String base);
    @Query(value = """
    SELECT COALESCE(SUM(t.quantity), 0) 
    FROM transfers t
    JOIN assets a ON t.asset_id = a.id
    WHERE LOWER(t.to_base) = LOWER(:base) 
    AND LOWER(a.type) = LOWER(:type)
    AND t.transfer_time BETWEEN :start AND :end""",
            nativeQuery = true)
    int getTransfersIn(@Param("base") String base,
                       @Param("type") String type,
                       @Param("start") LocalDate start,
                       @Param("end") LocalDate end);

    @Query(value = """
    SELECT COALESCE(SUM(t.quantity), 0) 
    FROM transfers t
    JOIN assets a ON t.asset_id = a.id
    WHERE LOWER(t.from_base) = LOWER(:base)
    AND LOWER(a.type) = LOWER(:type)
    AND t.transfer_time BETWEEN :start AND :end""",
            nativeQuery = true)
    int getTransfersOut(@Param("base") String base,
                        @Param("type") String type,
                        @Param("start") LocalDate start,
                        @Param("end") LocalDate end);
}