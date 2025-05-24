package com.example.military_asset_manager.repository;

import com.example.military_asset_manager.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    // Sum assigned assets before a date
    @Query("SELECT COALESCE(SUM(a.quantity), 0) FROM Assignment a " +
            "WHERE a.base = :base AND a.asset.type = :type " +
            "AND a.assignedAt <= CAST(:date AS LocalDateTime)")
    Integer sumAssignedByBaseAndTypeBeforeDate(
            @Param("base") String base,
            @Param("type") String type,
            @Param("date") LocalDate date
    );

    // Sum assigned assets within a date range
    @Query("SELECT COALESCE(SUM(a.quantity), 0) FROM Assignment a " +
            "WHERE a.base = :base AND a.asset.type = :type " +
            "AND a.assignedAt BETWEEN CAST(:start AS LocalDateTime) AND CAST(:end AS LocalDateTime)")
    Integer sumAssignedByBaseAndTypeBetweenDates(
            @Param("base") String base,
            @Param("type") String type,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    // Sum expended assets within a date range
    @Query("SELECT COALESCE(SUM(a.quantity), 0) FROM Assignment a " +
            "WHERE a.base = :base AND a.asset.type = :type " +
            "AND a.expendedAt BETWEEN CAST(:start AS LocalDateTime) AND CAST(:end AS LocalDateTime)")
    Integer sumExpendedByBaseAndTypeBetweenDates(
            @Param("base") String base,
            @Param("type") String type,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
}