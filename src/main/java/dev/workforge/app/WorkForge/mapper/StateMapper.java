package dev.workforge.app.WorkForge.mapper;

import dev.workforge.app.WorkForge.dto.StateDTO;
import dev.workforge.app.WorkForge.model.State;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StateMapper {
    StateMapper INSTANCE = Mappers.getMapper(StateMapper.class);

    StateDTO toDTO(State state);

    State fromDTO(StateDTO state);
}
