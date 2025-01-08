package ru.peregruzochka.travel_guide.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.peregruzochka.travel_guide.dto.CommentDto;
import ru.peregruzochka.travel_guide.mapper.CommentMapper;
import ru.peregruzochka.travel_guide.model.Comment;
import ru.peregruzochka.travel_guide.service.CommentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentMapper commentMapper;
    private final CommentService commentService;

    @PostMapping
    public CommentDto createComment(@RequestHeader(name = "app-user-id") UUID userId,
                                    @RequestBody @Valid CommentDto commentDto) {

        commentDto.setUserId(userId);
        Comment comment = commentMapper.toCommentEntity(commentDto);
        Comment savedComment = commentService.createComment(comment);
        return commentMapper.toCommentDto(savedComment);
    }

    @GetMapping("/search")
    public List<CommentDto> getCommentsBySightId(@RequestParam("sight-id") UUID sightId,
                                                 @RequestParam(defaultValue = "10") int limit,
                                                 @RequestParam(defaultValue = "0") int page) {
        List<Comment> comments = commentService.getCommentsBySightId(sightId, limit, page);
        return commentMapper.toListCommentDto(comments);
    }
}
