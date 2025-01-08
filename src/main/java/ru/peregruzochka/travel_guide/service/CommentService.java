package ru.peregruzochka.travel_guide.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.peregruzochka.travel_guide.model.Comment;
import ru.peregruzochka.travel_guide.model.Sight;
import ru.peregruzochka.travel_guide.model.User;
import ru.peregruzochka.travel_guide.repository.CommentRepository;
import ru.peregruzochka.travel_guide.repository.SightRepository;
import ru.peregruzochka.travel_guide.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final SightRepository sightRepository;
    private final UserRepository userRepository;

    @Transactional
    @Retryable(retryFor = ObjectOptimisticLockingFailureException.class)
    public Comment createComment(Comment comment) {
        UUID sightId = comment.getSight().getId();
        Sight sight = sightRepository.findById(sightId).orElseThrow();
        User user = userRepository.findById(comment.getUser().getId()).orElseThrow();

        long commentCount = commentRepository.countBySightId(sightId);

        int newGrade = comment.getGrade();
        float newAvgGrade = (sight.getAvgGrade() * commentCount + newGrade) / (commentCount + 1);
        sight.setAvgGrade(newAvgGrade);
        sightRepository.save(sight);

        comment.setSight(sight);
        comment.setUser(user);
        Comment savedComment = commentRepository.save(comment);
        log.info("Comment created: {}", savedComment);
        return savedComment;
    }

    @Transactional
    public List<Comment> getCommentsBySightId(UUID sightId, int limit, int page) {
        if (!sightRepository.existsById(sightId)) {
            throw new IllegalArgumentException("Sight does not exist");
        }
        Pageable pageable = PageRequest.of(page, limit);
        Page<Comment> comments = commentRepository.findBySightId(sightId, pageable);
        log.info("Get comments Count: {}", comments.getTotalElements());
        return comments.getContent();
    }
}
