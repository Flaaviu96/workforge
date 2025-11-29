package dev.workforge.app.WorkForge.mapper;

import dev.workforge.app.WorkForge.dto.TaskDTO;
import dev.workforge.app.WorkForge.dto.TaskMetadataDTO;
import dev.workforge.app.WorkForge.dto.TaskPatchResponseDTO;
import dev.workforge.app.WorkForge.model.PermissionType;
import dev.workforge.app.WorkForge.model.Task;
import dev.workforge.app.WorkForge.model.TaskMetadata;
import dev.workforge.app.WorkForge.projections.TaskProjection;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = CommentMapper.class)
public interface TaskMapper {

    @Mapping(target = "state", source = "state.name")
    @Mapping(target = "commentDTOS", source = "comments")
    @Mapping(target = "attachmentDTOS", source = "attachments")
    @Mapping(target = "taskMetadataDTO", source = "taskMetadata")
    @Mapping(target = "permissionTypes", ignore = true)
    TaskDTO toDTO(Task task, @Context List<PermissionType> permissionTypes);

    @AfterMapping
    default void setPermissionTypes(@MappingTarget TaskDTO.TaskDTOBuilder dtoBuilder,
                                    @Context List<PermissionType> permissionTypes) {
        dtoBuilder.permissionTypes(permissionTypes);
    }

    @Mapping(target = "state", source = "state.name")
    @Mapping(target = "commentDTOS", source = "comments")
    @Mapping(target = "taskMetadataDTO", source = "taskMetadata")
    TaskDTO toDTO(Task task);

    @Mapping(target = "project", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "attachments", ignore = true)
    @Mapping(target = "state", ignore = true)
    Task toTask(TaskDTO taskDTO);

    @Mapping(target = "commentDTO", source = "comments")
    List<TaskDTO> toDTO (Set<Task> tasks);

    @Mapping(target = "id", source = "taskId")
    @Mapping(target = "state", source = "stateName")
    @Mapping(target = "commentDTOS", ignore = true)
    @Mapping(target = "taskMetadataDTO.assignedTo", source = "assignedTo")
    @Mapping(target = "taskTimeTrackingDTO.remainingHours", source = "remainingHours", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    TaskDTO toTaskDTO(TaskProjection projection);

    List<TaskDTO> taskProjectionToDTO(List<TaskProjection> taskProjections);

    TaskMetadataDTO toTaskMetaDTO(TaskMetadata taskMetadata);

    @Mapping(target = "state", source = "state.name")
    @Mapping(target = "taskMetadataDTO", source = "taskMetadata")
    TaskPatchResponseDTO toTaskPathDTO(Task task);

}