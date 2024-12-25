package ru.peregruzochka.travel_guide.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Sight {

    @Id
    private UUID id;

    private String sightName;

    @ManyToOne
    private City city;

    @Enumerated(EnumType.STRING)
    private SightType type;

    private double lon;

    private double lat;

    @OneToMany
    private List<Comment> comments;

}
