package dev.workforge.app.WorkForge.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Date;

@Builder
public record CommentDTO(
        long id,

        @NotNull
        String author,

        @NotNull
        String content,

        @NotNull
        Date createdDate,

        @NotNull
        Date modifiedDate
) {}