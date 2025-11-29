package dev.workforge.app.WorkForge.mapper;

import dev.workforge.app.WorkForge.dto.CommentDTO;
import dev.workforge.app.WorkForge.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDTO toCommentDTO(Comment comment);
}
