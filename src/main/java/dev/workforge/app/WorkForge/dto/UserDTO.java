package dev.workforge.app.WorkForge.dto;

import lombok.Builder;

@Builder
public record UserDTO(
        String username,
        String password
) {}
