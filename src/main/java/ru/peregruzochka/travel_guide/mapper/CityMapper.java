package ru.peregruzochka.travel_guide.mapper;

import org.springframework.stereotype.Component;
import ru.peregruzochka.travel_guide.dto.CityDto;
import ru.peregruzochka.travel_guide.model.City;

@Component
public class CityMapper {

    public CityDto toCityDto(City city) {
        return CityDto.builder()
                .id(city.getId())
                .name(city.getCityName())
                .sightCount(getSightCount(city))
                .build();
    }

    private int getSightCount(City city) {
        return city.getSights() == null ? 0 : city.getSights().size();
    }
}
