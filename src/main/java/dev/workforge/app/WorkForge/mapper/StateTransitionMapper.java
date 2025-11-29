package dev.workforge.app.WorkForge.mapper;

import dev.workforge.app.WorkForge.dto.StateTransitionDTO;
import dev.workforge.app.WorkForge.model.StateTransition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = { StateMapper.class })
public interface StateTransitionMapper {

    StateTransitionMapper INSTANCE = Mappers.getMapper(StateTransitionMapper.class);

    @Mapping(target = "fromStateDTO", source = "fromState")
    @Mapping(target = "toStateDTO", source = "toState")
    StateTransitionDTO toDTO(StateTransition stateTransition);

    List<StateTransitionDTO> toDTOWithList(List<StateTransition> stateTransitionList);
}