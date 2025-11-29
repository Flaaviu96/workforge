package dev.workforge.app.WorkForge.dto;

import dev.workforge.app.WorkForge.model.PermissionType;
import lombok.Builder;

@Builder
public record PermissionDTO(
        long userId,
        PermissionType permissionType
) {}
