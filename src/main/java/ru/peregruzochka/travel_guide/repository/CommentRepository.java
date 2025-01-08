package ru.peregruzochka.travel_guide.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.peregruzochka.travel_guide.model.Comment;

import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    long countBySightId(UUID sightId);

    Page<Comment> findBySightId(UUID sightId, Pageable pageable);
}
