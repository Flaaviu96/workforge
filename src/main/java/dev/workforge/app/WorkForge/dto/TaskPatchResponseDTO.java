package dev.workforge.app.WorkForge.dto;

import java.util.Date;

public record TaskPatchResponseDTO(
        String taskName,
        String state,
        Date modifiedDate,
        TaskMetadataDTO taskMetadataDTO
) {}
