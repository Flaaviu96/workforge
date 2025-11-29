package dev.workforge.app.WorkForge.service.other;

import dev.workforge.app.WorkForge.model.PermissionType;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

public interface AccessControlService {

     /**
      * Checks if the user has the required permissions to access the specified project and whether their permissions have changed.
      *
      * @param projectId the ID of the project the user is trying to access
      * @param permissionTypes the required permission types (e.g., READ, WRITE, ADMIN) the user must have
      * @param sessionId the session ID of the user
      * @return true if the user has the required permissions
      * @throws AccessDeniedException if the user does not have the required permissions
      */
     boolean hasPermissions(Long projectId, List<PermissionType> permissionTypes, String sessionId);

     /**
      *
      * @return The ID's of the available projects that can be accessed
      */
     int[] getAvailableProjectsForCurrentUser();
}