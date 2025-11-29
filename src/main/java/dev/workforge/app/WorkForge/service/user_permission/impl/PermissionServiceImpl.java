package dev.workforge.app.WorkForge.service.user_permission.impl;

import dev.workforge.app.WorkForge.dto.PermissionDTO;
import dev.workforge.app.WorkForge.exceptions.PermissionException;
import dev.workforge.app.WorkForge.model.Permission;
import dev.workforge.app.WorkForge.model.PermissionType;
import dev.workforge.app.WorkForge.repository.PermissionRepository;
import dev.workforge.app.WorkForge.service.user_permission.PermissionService;
import dev.workforge.app.WorkForge.util.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    private PermissionType mapPermissionDTO(PermissionDTO permissionDTO) {
        return permissionDTO.permissionType();
    }

    @Override
    public List<Permission> getPermissionsByDTO(List<PermissionDTO> permissionDTOS) {
        if (permissionDTOS == null || permissionDTOS.isEmpty()) {
            return Collections.emptyList();
        }
        List<PermissionType> permissionTypeList = permissionDTOS.stream()
                .map(this::mapPermissionDTO)
                .distinct()
                .toList();
        List<Permission> permissions = permissionRepository.findPermissionByPermissionType(permissionTypeList);
        if (permissions.isEmpty()) {
            throw new PermissionException(ErrorMessages.PERMISSIONS_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return permissions;
    }

    @Override
    public List<Permission> getPermissionsByPermissionType(List<PermissionType> types) {
        if (types == null || types.isEmpty()) {
            return Collections.emptyList();
        }
        List<PermissionType> permissionTypeList = types.stream()
                .distinct()
                .toList();
        return fetchPermissions(permissionTypeList);
    }

    private List<Permission> fetchPermissions(List<PermissionType> types) {
        List<Permission> permissions = permissionRepository.findPermissionByPermissionType(types);
        if (permissions.isEmpty()) {
            throw new PermissionException(ErrorMessages.PERMISSIONS_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        return permissions;
    }
}
