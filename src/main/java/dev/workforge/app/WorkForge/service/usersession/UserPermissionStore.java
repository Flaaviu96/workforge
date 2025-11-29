package dev.workforge.app.WorkForge.service.usersession;

import dev.workforge.app.WorkForge.security.model.UserPrincipal;
import dev.workforge.app.WorkForge.security.user.PermissionContext;

public interface UserPermissionStore {
    void save(String username, PermissionContext permissionContext);
    UserPrincipal find(String username);
    void delete(String username);
    boolean hasKey(String username);
}
