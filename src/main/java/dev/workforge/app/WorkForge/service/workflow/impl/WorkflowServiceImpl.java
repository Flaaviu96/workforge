package dev.workforge.app.WorkForge.service.workflow.impl;

import dev.workforge.app.WorkForge.dto.StateDTO;
import dev.workforge.app.WorkForge.dto.WorkflowDTO;
import dev.workforge.app.WorkForge.exceptions.WorkflowException;
import dev.workforge.app.WorkForge.mapper.StateMapper;
import dev.workforge.app.WorkForge.model.State;
import dev.workforge.app.WorkForge.model.Workflow;
import dev.workforge.app.WorkForge.repository.WorkflowRepository;
import dev.workforge.app.WorkForge.service.workflow.WorkflowService;
import dev.workforge.app.WorkForge.trigger.AbstractTrigger;
import dev.workforge.app.WorkForge.util.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WorkflowServiceImpl implements WorkflowService {

    private final WorkflowRepository workflowRepository;
    private final WorkflowFactory workflowFactory;
    private final StateMapper stateMapper;

    public WorkflowServiceImpl(WorkflowRepository workflowRepository, WorkflowFactory workflowFactory, StateMapper stateMapper) {
        this.workflowRepository = workflowRepository;
        this.workflowFactory = workflowFactory;
        this.stateMapper = stateMapper;
    }

    @Override
    public Workflow getWorkflowById(long id) {
        return workflowRepository.findById(id)
                .orElseThrow(() -> new WorkflowException(ErrorMessages.WORKFLOW_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public boolean isTransitionValid(long id, String stateFrom, String stateTo) {
        if (!workflowFactory.hasWorkflow(id)) {
            Workflow workflow = workflowRepository.findWorkflowByProjectId(id);
            if (workflow == null) throw new WorkflowException(ErrorMessages.WORKFLOW_NOT_FOUND, HttpStatus.NOT_FOUND);
            workflowFactory.addWorkflow(workflow);
        }
        Map<State, AbstractTrigger> states = workflowFactory.getStatesTo(id, stateFrom);
        return states.entrySet().stream()
                .anyMatch(stateAbstractTriggerEntry -> stateAbstractTriggerEntry.getKey().getName().equals(stateTo));
    }

    @Override
    public State getStateToByName(long workflowId, String stateName) {
        return workflowFactory.getStateToByName(workflowId, stateName);
    }

    @Override
    public void triggerStateTransition(long workflowId, String stateFrom, State stateTo) {
        AbstractTrigger abstractTrigger = workflowFactory.getTrigger(workflowId, stateFrom, stateTo);
        if (abstractTrigger != null) {
            abstractTrigger.fire();
        }
    }

    @Override
    public WorkflowDTO getWorkflowByProjectId(int projectId) {
        if (!workflowFactory.hasWorkflow(projectId)) {
            Workflow workflow = workflowRepository.findWorkflowByProjectId(projectId);
            workflowFactory.addWorkflow(workflow);
        }
        Map<State, List<State>> stateDTOListMap = workflowFactory.getWorkflowForSpecificProject(projectId);
        if (stateDTOListMap == null) {
            throw new WorkflowException(ErrorMessages.WORKFLOW_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return new WorkflowDTO(projectId, transformWorkflowToDTO(stateDTOListMap));
    }

    private Map<String, List<StateDTO>> transformWorkflowToDTO(Map<State, List<State>> stateDTOListMap) {
        return stateDTOListMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getName(),
                        entry -> entry.getValue().stream()
                                .map(stateMapper::toDTO)
                                .collect(Collectors.toList())
                ));
    }
}
