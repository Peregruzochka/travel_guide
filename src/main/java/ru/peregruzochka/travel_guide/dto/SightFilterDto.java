package ru.peregruzochka.travel_guide.dto;

import lombok.Getter;
import lombok.Setter;
import ru.peregruzochka.travel_guide.model.SightType;

import java.util.List;

@Getter
@Setter
public class SightFilterDto {
    private List<SightType> types;
    private Float averageGrade;
}
