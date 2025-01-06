package ru.peregruzochka.travel_guide.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.peregruzochka.travel_guide.model.SightType;

import java.util.UUID;

@Builder
@Getter
@Setter
public class SightDto {
    private UUID id;
    private String sightName;
    private String description;
    private UUID cityId;
    private SightType sightType;
    private Double lat;
    private Double lon;
    private int commentsCount;
    private float avgGrade;
}
