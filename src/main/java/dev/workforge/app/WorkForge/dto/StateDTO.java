package dev.workforge.app.WorkForge.dto;

import dev.workforge.app.WorkForge.model.StateType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;



@Builder
public record StateDTO(
        @Min(1)
        long id,

        @NotNull
        String name,

        @NotNull
        StateType stateType
) {}
