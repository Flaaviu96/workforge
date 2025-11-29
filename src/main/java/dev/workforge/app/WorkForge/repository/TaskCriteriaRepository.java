package dev.workforge.app.WorkForge.repository;

import dev.workforge.app.WorkForge.dto.PageResultDTO;
import dev.workforge.app.WorkForge.dto.TaskFilter;
import dev.workforge.app.WorkForge.dto.TaskSummaryDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskCriteriaRepository {
    PageResultDTO<TaskSummaryDTO> findTasksByFilter(TaskFilter taskFilter, long projectId);
}
