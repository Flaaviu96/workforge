package dev.workforge.app.WorkForge.projections;

import dev.workforge.app.WorkForge.model.Permission;

import java.util.List;


public interface UserPermissionProjection {
     Long getProjectId();
     List<Permission> getPermissions();
}
