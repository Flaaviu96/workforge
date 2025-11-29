package dev.workforge.app.WorkForge.service.task.impl;

import dev.workforge.app.WorkForge.dto.CommentDTO;
import dev.workforge.app.WorkForge.exceptions.CommentException;
import dev.workforge.app.WorkForge.mapper.CommentMapper;
import dev.workforge.app.WorkForge.model.Comment;
import dev.workforge.app.WorkForge.model.Task;
import dev.workforge.app.WorkForge.repository.CommentRepository;
import dev.workforge.app.WorkForge.service.task.CommentService;
import dev.workforge.app.WorkForge.util.ErrorMessages;
import jakarta.persistence.OptimisticLockException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDTO saveNewComment(Task task, long projectId, CommentDTO commentDTO) {
        try {
            if (!hasRequiredFields(commentDTO)) {
                throw new CommentException(ErrorMessages.COMMENT_INVALID, HttpStatus.BAD_REQUEST);
            }

            Set<Comment> comments = task.getComments();
            Comment comment = new Comment();
            comment.setTask(task);
            comment.setProjectId(projectId);
            comment.setContent(commentDTO.content());
            comment.setAuthor(commentDTO.author());
            comments.add(comment);

            Comment savedComment = commentRepository.saveAndFlush(comment);
            return CommentMapper.INSTANCE.toCommentDTO(savedComment);
        } catch (OptimisticLockException e) {
            throw new OptimisticLockException("Another used has added a comment at the same time. Please try again");
        }
    }

    @Override
    public CommentDTO updateComment(Task task, CommentDTO commentDTO) {
        Set<Comment> comments = task.getComments();

        if (comments == null || comments.isEmpty()) {
            throw new CommentException("No comments found for the task", HttpStatus.NOT_FOUND);
        }
        Comment comment = comments.stream()
                .filter(c -> c.getId() == commentDTO.id())
                .findFirst()
                .orElseThrow(() -> new CommentException("Comment not found", HttpStatus.NOT_FOUND));

        comment.setContent(commentDTO.content());
        commentRepository.save(comment);
        return CommentMapper.INSTANCE.toCommentDTO(comment);
    }

    private boolean hasRequiredFields(CommentDTO comment) {
        return comment.author() != null && !comment.author().isEmpty()
                && comment.content() != null && !comment.content().isEmpty();
    }
}
