package dev.workforge.app.WorkForge.service.project.impl;

import dev.workforge.app.WorkForge.dto.*;
import dev.workforge.app.WorkForge.enums.GlobalEnum;
import dev.workforge.app.WorkForge.exceptions.ProjectException;
import dev.workforge.app.WorkForge.exceptions.TaskException;
import dev.workforge.app.WorkForge.exceptions.WorkflowException;
import dev.workforge.app.WorkForge.mapper.ProjectMapper;
import dev.workforge.app.WorkForge.mapper.TaskMapper;
import dev.workforge.app.WorkForge.model.*;
import dev.workforge.app.WorkForge.repository.ProjectRepository;
import dev.workforge.app.WorkForge.service.project.ProjectWriteService;
import dev.workforge.app.WorkForge.service.user_permission.UserPermissionService;
import dev.workforge.app.WorkForge.service.user_permission.UserService;
import dev.workforge.app.WorkForge.service.workflow.WorkflowService;
import dev.workforge.app.WorkForge.util.ErrorMessages;
import dev.workforge.app.WorkForge.util.ProjectKeyGenerator;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectWriteServiceImpl implements ProjectWriteService {

    private final ProjectRepository projectRepository;
    private final WorkflowService workflowService;
    private final ProjectMapper projectMapper;
    private final TaskMapper taskMapper;
    private final UserPermissionService userPermissionService;

    public ProjectWriteServiceImpl(ProjectRepository projectRepository, WorkflowService workflowService, ProjectMapper projectMapper, TaskMapper taskMapper, UserService userService, UserPermissionService userPermissionService) {
        this.projectRepository = projectRepository;
        this.workflowService = workflowService;
        this.projectMapper = projectMapper;
        this.taskMapper = taskMapper;
        this.userPermissionService = userPermissionService;
    }

    @Transactional
    @Override
    public TaskDTO saveNewTaskIntoProject(long projectId, TaskDTO taskDTO) {

        if (taskDTO != null && (taskDTO.taskName().isEmpty() || taskDTO.taskName().isBlank())) {
            throw new TaskException(ErrorMessages.INVALID_ID, HttpStatus.BAD_REQUEST);
        }

        for (int counter = 0; counter < 3; counter++) {
            try {
                Optional<Project> optionalProject = projectRepository.findProjectWithTasks(projectId);
                if (optionalProject.isEmpty()) {
                    throw new ProjectException(ErrorMessages.PROJECT_NOT_FOUND, HttpStatus.NOT_FOUND);
                }
                Project project = optionalProject.get();
                Task task = taskMapper.toTask(taskDTO);
                project.getTasks().add(task);
                task.setProject(optionalProject.get());
            } catch (OptimisticLockException e) {
                throw new ProjectException(ErrorMessages.PROJECT_UPDATE_FAILED, HttpStatus.BAD_REQUEST);
            }
        }

        return taskDTO;
    }

    @Transactional
    @Override
    public ProjectDTO updateProjectPartially(long projectId, ProjectDTO projectDTO) {
        Optional<Project> optionalProject = projectRepository.findProjectWithWorkflow(projectId);

        if (optionalProject.isEmpty()) {
            throw new ProjectException(ErrorMessages.PROJECT_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        Project project = optionalProject.get();

        if (projectDTO.projectName() != null && !projectDTO.projectName().isEmpty()) {
            project.setProjectName(projectDTO.projectName());
        }
        if (projectDTO.projectDescription() != null && !projectDTO.projectDescription().isEmpty()) {
            project.setProjectDescription(projectDTO.projectDescription());
        }
        if (projectDTO.workflowId() != 0 && project.getWorkflow().getId() != projectDTO.workflowId()) {
            Workflow workflow = workflowService.getWorkflowById(projectDTO.workflowId());
            project.setWorkflow(workflow);
          //  projectDTO.transitions().putAll(getWorkflowStateTransitionMap(workflow.getId()));
        }

        return projectMapper.toDTOWithoutTasks(project);
    }

    @Override
    public ProjectDTO saveNewProject(CreateProjectDTO createProjectDTO) {
        validateNewProject(createProjectDTO);
        Project project = projectMapper.createProject(createProjectDTO);
        Workflow workflow = workflowService.getWorkflowById(GlobalEnum.DEFAULT_WORKFLOW.getId());
        if (workflow == null) {
            throw new WorkflowException(ErrorMessages.WORKFLOW_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        project.setWorkflow(workflow);
        project.setProjectKey(generateProjectKey(createProjectDTO.projectName()));
        projectRepository.save(project);
        userPermissionService.createDefaultOwnerPermissions(UUID.fromString(createProjectDTO.projectOwner()), project);
        return projectMapper.toDTOWithoutTasks(project);
    }

    private void validateNewProject(CreateProjectDTO dto) {
        if (dto.projectName() == null || dto.projectName().isBlank()) {
            throw new ProjectException(ErrorMessages.INVALID_ID, HttpStatus.BAD_REQUEST);
        }

        if (projectRepository.existsByProjectName(dto.projectName())) {
            throw new ProjectException(ErrorMessages.PROJECT_INVALID, HttpStatus.BAD_REQUEST);
        }
    }

    private String generateProjectKey(String projectName) {
        if (projectRepository.existsByProjectName(projectName)) {
            throw new ProjectException(ErrorMessages.PROJECT_INVALID, HttpStatus.BAD_REQUEST);
        }
        return ProjectKeyGenerator.generateKey(projectName);
    }
}
