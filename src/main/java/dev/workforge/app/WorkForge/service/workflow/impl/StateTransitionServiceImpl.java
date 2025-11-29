package dev.workforge.app.WorkForge.service.workflow.impl;

import dev.workforge.app.WorkForge.model.StateTransition;
import dev.workforge.app.WorkForge.repository.StateTransitionRepository;
import dev.workforge.app.WorkForge.service.workflow.StateTransitionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateTransitionServiceImpl implements StateTransitionService {

    private final StateTransitionRepository stateTransitionRepository;

    public StateTransitionServiceImpl(StateTransitionRepository stateTransitionRepository) {
        this.stateTransitionRepository = stateTransitionRepository;
    }

    @Override
    public List<StateTransition> getStatesTransitionsByWorkflowId(long workflowId) {
        return stateTransitionRepository.findAllStateTransitionByWorkflowId(workflowId);
    }
}
