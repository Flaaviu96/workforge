package dev.workforge.app.WorkForge.dto;

import lombok.Builder;

@Builder
public record AttachmentDTO(
        long id,
        String fileName
) {}
