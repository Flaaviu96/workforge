package dev.workforge.app.WorkForge.dto;

import java.util.Date;

public record TaskSummaryDTO(
        long taskId,
        String taskName,
        String state,
        Date createdDate,
        String assignedTo
) {}
