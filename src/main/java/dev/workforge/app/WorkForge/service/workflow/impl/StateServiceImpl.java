package dev.workforge.app.WorkForge.service.workflow.impl;

import dev.workforge.app.WorkForge.model.State;
import dev.workforge.app.WorkForge.model.StateType;
import dev.workforge.app.WorkForge.repository.StateRepository;
import dev.workforge.app.WorkForge.service.workflow.StateService;
import org.springframework.stereotype.Service;

@Service
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;

    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public State loadStateByStateType(StateType stateType) {
        return stateRepository.findStateByStateType(stateType);
    }
}
