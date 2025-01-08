package ru.peregruzochka.travel_guide.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.peregruzochka.travel_guide.model.Comment;
import ru.peregruzochka.travel_guide.model.Sight;
import ru.peregruzochka.travel_guide.model.User;
import ru.peregruzochka.travel_guide.repository.CommentRepository;
import ru.peregruzochka.travel_guide.repository.SightRepository;
import ru.peregruzochka.travel_guide.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private SightRepository sightRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;

    private Sight sight;
    private User user;
    private Comment comment;

    @BeforeEach
    void setUp() {
        sight = new Sight();
        sight.setId(UUID.randomUUID());
        sight.setAvgGrade(4.5f);

        user = new User();
        user.setId(UUID.randomUUID());

        comment = new Comment();
        comment.setId(UUID.randomUUID());
        comment.setSight(sight);
        comment.setUser(user);
        comment.setGrade(5);
    }

    @Test
    void testCreateComment() {
        UUID sightId = sight.getId();
        when(sightRepository.findById(sightId)).thenReturn(Optional.of(sight));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(commentRepository.countBySightId(sightId)).thenReturn(10L);

        commentService.createComment(comment);

        verify(sightRepository, times(1)).save(sight);
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void testGetCommentsBySightId() {
        UUID sightId = sight.getId();
        int limit = 5;
        int page = 0;

        when(sightRepository.existsById(sightId)).thenReturn(true);
        Page<Comment> commentPage = new PageImpl<>(Collections.singletonList(comment), PageRequest.of(page, limit), 1);
        when(commentRepository.findBySightId(sightId, PageRequest.of(page, limit))).thenReturn(commentPage);

        List<Comment> comments = commentService.getCommentsBySightId(sightId, limit, page);

        assertNotNull(comments);
        assertEquals(1, comments.size());
        assertEquals(comment, comments.get(0));

        verify(sightRepository).existsById(sightId);
        verify(commentRepository).findBySightId(sightId, PageRequest.of(page, limit));
    }

    @Test
    void testGetCommentsBySightId_SightDoesNotExist() {
        UUID sightId = UUID.randomUUID();

        when(sightRepository.existsById(sightId)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                commentService.getCommentsBySightId(sightId, 5, 0));

        assertEquals("Sight does not exist", exception.getMessage());

        verify(sightRepository).existsById(sightId);
        verifyNoInteractions(commentRepository);
    }
}

