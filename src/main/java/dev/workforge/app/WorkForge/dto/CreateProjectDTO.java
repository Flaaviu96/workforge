package dev.workforge.app.WorkForge.dto;

import lombok.Builder;

@Builder
public record CreateProjectDTO (
        String projectName,
        String projectDescription,
        String projectOwner
) {}

