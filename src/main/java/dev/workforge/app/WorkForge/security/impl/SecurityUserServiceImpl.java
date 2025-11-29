package dev.workforge.app.WorkForge.security.impl;

import dev.workforge.app.WorkForge.model.Permission;
import dev.workforge.app.WorkForge.model.PermissionType;
import dev.workforge.app.WorkForge.projections.UserPermissionProjection;
import dev.workforge.app.WorkForge.security.model.UserPrincipal;
import dev.workforge.app.WorkForge.security.SecurityUserService;
import dev.workforge.app.WorkForge.service.user_permission.UserPermissionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SecurityUserServiceImpl implements SecurityUserService {

    private final UserPermissionService userPermissionService;

    public SecurityUserServiceImpl(UserPermissionService userPermissionService) {
        this.userPermissionService = userPermissionService;
    }

    @Override
    public void loadUserPermissionsIntoUserDetails(UserPrincipal userPrincipal) {
        List<UserPermissionProjection> userPermission = userPermissionService.getPermissionsForUser(userPrincipal.getUsername());
        addPermissionsToUser(userPermission, false);
    }

    @Override
    public void refreshUserPermissionsForUserDetails(UserPrincipal userPrincipal) {
        List<UserPermissionProjection> userPermission = userPermissionService.getPermissionsForUser(userPrincipal.getUsername());
        addPermissionsToUser(userPermission, true);
    }

    @Override
    public List<PermissionType> getProjectPermissionForUser(long projectId) {
        Map<Long, Set<Permission>> permissions = getPermissionContext().getPermissionMap();
        if (!permissions.isEmpty()) {
            return permissions.get(projectId).stream()
                    .map(Permission::getPermissionType)
                    .toList();
        }
        return null;
    }

    private void addPermissionsToUser(List<UserPermissionProjection> userPermissionList, boolean updatePermissions) {
        if (userPermissionList.isEmpty()) {
            return;
        }
        if (updatePermissions) {
            getPermissionContextOperation().clearMap();
        }

        for (UserPermissionProjection userPermission : userPermissionList) {
            getPermissionContextOperation().addPermissions(userPermission.getProjectId(), userPermission.getPermissions());
        }
    }

    public PermissionContext getPermissionContext() {
        return ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPermissionContext();
    }

    public PermissionContextOperation getPermissionContextOperation() {
        Object permissionContext = getPermissionContext();
        if (permissionContext instanceof PermissionContextOperation permissionContextOperation) {
            return permissionContextOperation;
        }
        throw new IllegalStateException("Principal does not implement PermissionContextOperation");
    }

    public PermissionContext getPermissionContext(UserPrincipal user) {
        return findPermissionContext(user)
                .orElseThrow(() -> new IllegalStateException("PermissionContext not found for user"));
    }

    private Optional<PermissionContext> findPermissionContext(UserPrincipal user) {
        return Optional.ofNullable(user)
                .map(UserPrincipal::getPermissionContext);
    }

    public PermissionContextOperation getPermissionContextOperation(UserPrincipal user) {
        if (user.getPermissionContext() instanceof PermissionContextOperation permissionContextOperation) {
            return permissionContextOperation;
        }
        throw new IllegalArgumentException("User does not implement PermissionContextOperation");
    }


    @Override
    public UserPrincipal retrieveSecurityUser() {
        return ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
