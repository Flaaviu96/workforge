package dev.workforge.app.WorkForge.security;

import dev.workforge.app.WorkForge.model.PermissionType;
import dev.workforge.app.WorkForge.security.model.UserPrincipal;

import java.util.List;

public interface SecurityUserService {

    /**
     * Loads and attaches user permissions from the database into the given UserDetails object.
     * This enriches the UserDetails with up-to-date permission information for authorization purposes.
     *
     * @param userPrincipal the UserDetails instance to load permissions into
     */
    void loadUserPermissionsIntoUserDetails(UserPrincipal userPrincipal);

    /**
     * Retrieves the current permission context for the logged-in user.
     *
     * @return the PermissionContext representing the user's permission scope
     */
    PermissionContext getPermissionContext();

    /**
     * Retrieves the current permission context operation for the logged-in user.
     *
     * @return the PermissionContextOperation indicating what operations the user can perform
     */
    PermissionContextOperation getPermissionContextOperation();

    /**
     * Retrieves the SecurityUser object representing the currently authenticated user.
     *
     * @return the SecurityUser instance of the authenticated user
     */
    UserPrincipal retrieveSecurityUser();

    /**
     * Retrieves the permission context for a specific SecurityUser.
     *
     * @param user the SecurityUser for whom the permission context is fetched
     * @return the PermissionContext representing the user's permission scope
     */
    PermissionContext getPermissionContext(UserPrincipal user);

    /**
     * Retrieves the permission context operation for a specific SecurityUser.
     *
     * @param user the SecurityUser for whom the permission context operation is fetched
     * @return the PermissionContextOperation indicating the operations the user can perform
     */
    PermissionContextOperation getPermissionContextOperation(UserPrincipal user);

    /**
     * Refreshes the permissions of the given UserDetails object, updating it with the latest permissions.
     *
     * @param userPrincipal the UserDetails whose permissions are to be refreshed
     */
    void refreshUserPermissionsForUserDetails(UserPrincipal userPrincipal);

    /**
     * Retrieves the list of PermissionType values the user has for a specific project.
     *
     * @param projectId the ID of the project for which permissions are queried
     * @return a list of PermissionType enums representing the user's permissions on the project
     */
    List<PermissionType> getProjectPermissionForUser(long projectId);
}
