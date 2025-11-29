package dev.workforge.app.WorkForge.service.workflow;

import dev.workforge.app.WorkForge.model.StateTransition;

import java.util.List;

public interface StateTransitionService {

    List<StateTransition> getStatesTransitionsByWorkflowId(long workflowId);
}
