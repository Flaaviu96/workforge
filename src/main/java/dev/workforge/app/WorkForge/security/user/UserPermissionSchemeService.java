package dev.workforge.app.WorkForge.security.user;

import dev.workforge.app.WorkForge.security.PermissionContext;
import dev.workforge.app.WorkForge.security.SecurityUserService;
import dev.workforge.app.WorkForge.security.user.PermissionContext;
import dev.workforge.app.WorkForge.security.model.UserPrincipal;
import dev.workforge.app.WorkForge.service.usersession.UserPermissionStore;
import org.springframework.stereotype.Service;

@Service
public class UserPermissionSchemeService {
    private final UserPermissionStore userPermissionStore;
    private final SecurityUserService securityUserService;

    public UserPermissionSchemeService(UserPermissionStore userPermissionStore, SecurityUserService securityUserService) {
        this.userPermissionStore = userPermissionStore;
        this.securityUserService = securityUserService;
    }


    public void storeUserPermissionInRedis(String username, UserPrincipal userPrincipal) {
        if (username != null
                && userPrincipal != null
                && userPrincipal.getPermissionContext() != null
        ) {
            PermissionContext permissionContext = userPrincipal.getPermissionContext();
            userPermissionStore.save(username, permissionContext);
        }
    }

    public void updateTimeStamp(String username) {
        UserPrincipal userPrincipal = userPermissionStore.find(username);
        if (userPrincipal == null) {
            return;
        }
        securityUserService.getPermissionContext(userPrincipal).setUpdatedPermission(System.currentTimeMillis());
        userPermissionStore.save(username, userPrincipal.getPermissionContext());
    }

        public boolean hasKey(String username) {
        return userPermissionStore.hasKey(username);
    }
}
