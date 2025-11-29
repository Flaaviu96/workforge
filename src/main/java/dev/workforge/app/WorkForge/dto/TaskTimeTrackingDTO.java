package dev.workforge.app.WorkForge.dto;

public record TaskTimeTrackingDTO(
        double estimatedHours,
        double loggedHours,
        double remainingHours
) {}
