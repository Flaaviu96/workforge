package dev.workforge.app.WorkForge.repository;

import dev.workforge.app.WorkForge.model.Permission;
import dev.workforge.app.WorkForge.model.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query(
            "SELECT p FROM Permission p " +
            "WHERE p.permissionType IN :permissionType "
    )
    List<Permission> findPermissionByPermissionType(@Param("permissionType") List<PermissionType> permissionTypes);
}
