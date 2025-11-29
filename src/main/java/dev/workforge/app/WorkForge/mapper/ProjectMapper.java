package dev.workforge.app.WorkForge.mapper;

import dev.workforge.app.WorkForge.dto.CreateProjectDTO;
import dev.workforge.app.WorkForge.dto.ProjectDTO;
import dev.workforge.app.WorkForge.model.Project;
import dev.workforge.app.WorkForge.projections.ProjectProjection;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.*;

@Mapper(componentModel = "spring", uses = TaskMapper.class)
public interface ProjectMapper {

    ProjectDTO toDTOWithTasks(ProjectProjection projectProjection);

    Project createProject(CreateProjectDTO createProjectDTO);

    @Named("toDTOWithoutTasks")
    @Mapping(target = "tasks", ignore = true)
    ProjectDTO toDTOWithoutTasks(Project project);

    @IterableMapping(qualifiedByName = "toDTOWithoutTasks")
    List<ProjectDTO> toProjectsDTOWithoutTasks(List<Project> projects);
}
