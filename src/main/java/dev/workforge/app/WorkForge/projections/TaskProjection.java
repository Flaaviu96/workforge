package dev.workforge.app.WorkForge.projections;

public interface TaskProjection {
    String getTaskName();
    String getStateName();
    String getAssignedTo();
    Double getRemainingHours();
    Long getTaskId();

}
