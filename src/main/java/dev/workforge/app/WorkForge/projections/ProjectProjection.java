package dev.workforge.app.WorkForge.projections;

import java.util.Set;

public interface ProjectProjection {
    Long getProjectId();
    String getProjectName();
    Set<TaskProjection> getTasks();
}
