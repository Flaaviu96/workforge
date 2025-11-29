package dev.workforge.app.WorkForge.service.workflow;

import dev.workforge.app.WorkForge.model.State;
import dev.workforge.app.WorkForge.model.StateType;

public interface StateService {

    State loadStateByStateType(StateType stateType);
}
