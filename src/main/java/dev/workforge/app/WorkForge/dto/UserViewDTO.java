package dev.workforge.app.WorkForge.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserViewDTO(
        UUID uuid,
        String username
) {}
