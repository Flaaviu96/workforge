package dev.workforge.app.WorkForge.dto;

import lombok.Builder;

@Builder
public record TaskMetadataDTO(
     String assignedTo,
     String description
) {}
