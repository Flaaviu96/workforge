package dev.workforge.app.WorkForge.service.impl;

import dev.workforge.app.WorkForge.exceptions.PermissionException;
import dev.workforge.app.WorkForge.model.Permission;
import dev.workforge.app.WorkForge.model.PermissionType;
import dev.workforge.app.WorkForge.security.user.UserPermissionSchemeService;
import dev.workforge.app.WorkForge.security.model.UserPrincipal;
import dev.workforge.app.WorkForge.service.other.AccessControlService;
import dev.workforge.app.WorkForge.security.SecurityUserService;
import dev.workforge.app.WorkForge.util.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class AccessControlServiceImpl implements AccessControlService {

    private final SecurityUserService securityUserService;
    private final UserPermissionSchemeService userPermissionService;

    public AccessControlServiceImpl(SecurityUserService securityUserService, UserPermissionSchemeService userPermissionService) {
        this.securityUserService = securityUserService;
        this.userPermissionService = userPermissionService;
    }

    @Override
    public boolean hasPermissions(Long projectId, List<PermissionType> permissionTypes, String sessionId) {
        if (projectId == null) {
            return false;
        }

        UserPrincipal userPrincipal = securityUserService.retrieveSecurityUser();

        if (sessionId != null && hasPermissionsChanged(sessionId)) {
            securityUserService.refreshUserPermissionsForUserDetails(userPrincipal);
            securityUserService.getPermissionContextOperation(userPrincipal).rebuildTimestamps();
            userPermissionService.storeUserPermissionInRedis(sessionId, userPrincipal);
        }

        Map<Long, Set<Permission>> permissions = userPrincipal.getPermissionContext().getPermissionMap();

        if (permissions.containsKey(projectId) && hasRequiredPermissions(permissionTypes, permissions, projectId)) {
            return true;
        }

        throw new PermissionException(ErrorMessages.PROJECT_VIEW_PERMISSION_DENIED, HttpStatus.FORBIDDEN);
    }

    /**
     * Checks if the user has the required permissions to access the specified project.
     *
     * @param permissionTypes the required permission types (e.g., READ, WRITE, ADMIN) the user must have
     * @param permissions a map of project IDs to the sets of permissions assigned to the user
     * @param projectId the ID of the project the user is trying to access
     * @return true if the user has all required permissions for the project and does not have WRITE without READ; false otherwise
     */
    private boolean hasRequiredPermissions(List<PermissionType> permissionTypes, Map<Long, Set<Permission>> permissions, long projectId) {
        Set<Permission> permissionSet = permissions.get(projectId);
        if (permissionSet == null) {
            return false;
        }

        if (hasWriteWithoutRead(permissionSet)) {
            return false;
        }
        for (PermissionType permissionType : permissionTypes) {
            boolean result = permissionSet.stream().noneMatch(permission -> permission.getPermissionType() == permissionType);
            if (result) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int[] getAvailableProjectsForCurrentUser() {

        Map<Long, Set<Permission>> permissions = securityUserService.getPermissionContext().getPermissionMap();
        return permissions.keySet().stream()
                .mapToInt(Long::intValue)
                .toArray();
    }

    /**
     * Checks if the user has WRITE permission but does not have READ permission.
     *
     * @param permissions the set of permissions assigned to the user
     * @return true if the user has WRITE permission and does not have READ permission; false otherwise
     */
    private boolean hasWriteWithoutRead(Set<Permission> permissions) {
        boolean hasWrite = permissions.stream().anyMatch(permission -> permission.getPermissionType() == PermissionType.WRITE);
        boolean hasRead = permissions.stream().anyMatch(permission -> permission.getPermissionType() == PermissionType.READ);

        return hasWrite && !hasRead;
    }

    /**
     * Checks if any permissions for the current user have changed.
     *
     * @param sessionId the session ID of the user
     * @return true if the user's permissions have changed; false otherwise
     */
    private boolean hasPermissionsChanged(String sessionId) {
        UserPrincipal securityUser = securityUserService.retrieveSecurityUser();
        long permissionBuildAt = securityUser.getPermissionContext().getBuildPermissionAt();
        long permissionUpdatedAt = securityUser.getPermissionContext().getUpdatedPermission();
        return permissionUpdatedAt > permissionBuildAt &&
                (permissionUpdatedAt - permissionBuildAt >= 2_00);
    }
}
