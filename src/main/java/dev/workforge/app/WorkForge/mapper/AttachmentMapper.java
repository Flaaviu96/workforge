package dev.workforge.app.WorkForge.mapper;

import dev.workforge.app.WorkForge.dto.AttachmentDTO;
import dev.workforge.app.WorkForge.model.Attachment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {

    AttachmentMapper INSTANCE = Mappers.getMapper(AttachmentMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "fileName", target = "fileName")
    AttachmentDTO toDTO (Attachment attachment);
}
