package ru.peregruzochka.travel_guide.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class User {

    @Id
    private UUID id;

    private String name;

    @OneToMany
    private List<Comment> comments;
}
