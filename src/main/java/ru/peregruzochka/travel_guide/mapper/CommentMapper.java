package ru.peregruzochka.travel_guide.mapper;

import org.springframework.stereotype.Component;
import ru.peregruzochka.travel_guide.dto.CommentDto;
import ru.peregruzochka.travel_guide.model.Comment;
import ru.peregruzochka.travel_guide.model.Sight;
import ru.peregruzochka.travel_guide.model.User;

import java.util.List;

@Component
public class CommentMapper {
    public Comment toCommentEntity(CommentDto commentDto) {
        return Comment.builder()
                .user(User.builder()
                        .id(commentDto.getUserId())
                        .build())
                .content(commentDto.getContent())
                .grade(commentDto.getGrade())
                .sight(Sight.builder()
                        .id(commentDto.getSightId())
                        .build())
                .build();
    }

    public CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .sightId(comment.getSight().getId())
                .content(comment.getContent())
                .grade(comment.getGrade())
                .userId(comment.getUser().getId())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public List<CommentDto> toListCommentDto(List<Comment> comments) {
        return comments.stream()
                .map(this::toCommentDto)
                .toList();
    }
}
