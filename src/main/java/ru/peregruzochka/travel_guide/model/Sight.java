package ru.peregruzochka.travel_guide.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "sights")
public class Sight {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "sight_name", nullable = false)
    private String sightName;

    @Column(name = "sight_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(name = "sight_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SightType type;

    @Column(name = "lon", nullable = false)
    private double lon;

    @Column(name = "lat", nullable = false)
    private double lat;

    @OneToMany(mappedBy = "sight")
    private List<Comment> comments;
}
