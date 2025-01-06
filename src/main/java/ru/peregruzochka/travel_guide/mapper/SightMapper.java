package ru.peregruzochka.travel_guide.mapper;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;
import ru.peregruzochka.travel_guide.dto.SightDto;
import ru.peregruzochka.travel_guide.model.Sight;

import java.util.List;

@Component
public class SightMapper {

    public List<SightDto> toSightDtoList(List<Sight> sights) {
        return sights.stream()
                .map(this::toSightDto)
                .toList();
    }

    public SightDto toSightDto(Sight sight) {
        return SightDto.builder()
                .id(sight.getId())
                .sightName(sight.getSightName())
                .description(sight.getDescription())
                .cityId(sight.getCity().getId())
                .sightType(sight.getType())
                .lat(getLat(sight))
                .lon(getLon(sight))
                .commentsCount(getCommentCount(sight))
                .avgGrade(sight.getAvgGrade())
                .build();
    }

    private double getLat(Sight sight) {
        Point point = sight.getGeom();
        return point.getX();
    }

    private double getLon(Sight sight) {
        Point point = sight.getGeom();
        return point.getY();
    }

    private int getCommentCount(Sight sight) {
        return sight.getComments() == null ? 0 : sight.getComments().size();
    }
}
