package dev.workforge.app.WorkForge.dto;

import lombok.Builder;

@Builder
public record StateTransitionDTO(
        long id,
        StateDTO fromStateDTO,
        StateDTO toStateDTO,
        WorkflowDTO workflowDTO
) {}
