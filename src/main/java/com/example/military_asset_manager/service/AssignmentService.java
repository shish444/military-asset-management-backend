package com.example.military_asset_manager.service;

import com.example.military_asset_manager.model.Asset;
import com.example.military_asset_manager.model.Assignment;
import com.example.military_asset_manager.repository.AssetRepository;
import com.example.military_asset_manager.repository.AssignmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final AssetRepository assetRepository;

    public AssignmentService(AssignmentRepository assignmentRepository, AssetRepository assetRepository) {
        this.assignmentRepository = assignmentRepository;
        this.assetRepository = assetRepository;
    }

    public Assignment assignAsset(Long assetId, String base, String assignedTo, int quantity) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found with ID: " + assetId));

        Assignment assignment = new Assignment(asset, base, assignedTo, quantity, LocalDateTime.now());
        return assignmentRepository.save(assignment);
    }

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Assignment markAsExpended(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + assignmentId));

        if (assignment.isExpended()) {
            throw new RuntimeException("Assignment already marked as expended.");
        }

        assignment.setExpended(true);
        assignment.setExpendedAt(LocalDateTime.now());
        return assignmentRepository.save(assignment);
    }
}
