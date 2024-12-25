package ru.peregruzochka.travel_guide.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Comment {

    @Id
    private UUID id;

    @ManyToOne
    private Sight sight;

    @ManyToOne
    private User user;

    private String content;

    private int grade;

    private LocalDateTime createdAt;
}
