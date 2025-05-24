package com.example.military_asset_manager.controller;

import com.example.military_asset_manager.model.Assignment;
import com.example.military_asset_manager.service.AssignmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = "*")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping
    public Assignment assignAsset(@RequestBody Map<String, Object> payload) {
        Long assetId = Long.valueOf(payload.get("assetId").toString());
        String base = payload.get("base").toString();
        String assignedTo = payload.get("assignedTo").toString();
        int quantity = Integer.parseInt(payload.get("quantity").toString());

        return assignmentService.assignAsset(assetId, base, assignedTo, quantity);
    }

    @GetMapping
    public List<Assignment> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    @PutMapping("/{id}/expend")
    public Assignment markAsExpended(@PathVariable Long id) {
        return assignmentService.markAsExpended(id);
    }
}
