package ru.peregruzochka.travel_guide.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.peregruzochka.travel_guide.model.SightType;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SightDto {
    private UUID id;

    @NotBlank(message = "sightName can`t be null or empty")
    private String sightName;
    private String description;

    @NotNull(message = "cityId can`t be null")
    private UUID cityId;

    @NotNull(message = "sightType can`be null")
    private SightType sightType;

    @NotNull(message = "lat can`be null")
    private Double lat;

    @NotNull(message = "lon can`be null")
    private Double lon;

    private int commentsCount;
    private float avgGrade;
}
