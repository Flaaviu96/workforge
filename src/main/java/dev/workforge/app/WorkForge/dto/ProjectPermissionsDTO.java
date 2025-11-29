package dev.workforge.app.WorkForge.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ProjectPermissionsDTO(
        long projectId,
        List<PermissionDTO> permissionDTO
) {}
