package ru.peregruzochka.travel_guide.mapper;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Component;
import ru.peregruzochka.travel_guide.dto.SightDto;
import ru.peregruzochka.travel_guide.model.City;
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

    public Sight toSightEntity(SightDto sightDto) {
        return Sight.builder()
                .sightName(sightDto.getSightName())
                .description(sightDto.getDescription())
                .city(City.builder()
                        .id(sightDto.getCityId())
                        .build())
                .type(sightDto.getSightType())
                .location(createPoint(sightDto))
                .build();
    }

    private double getLat(Sight sight) {
        Point point = sight.getLocation();
        return point.getY();
    }

    private double getLon(Sight sight) {
        Point point = sight.getLocation();
        return point.getX();
    }

    private int getCommentCount(Sight sight) {
        return sight.getComments() == null ? 0 : sight.getComments().size();
    }

    private Point createPoint(SightDto sightDto) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

        double lat = sightDto.getLat();
        double lon = sightDto.getLon();

        return geometryFactory.createPoint(new Coordinate(lon, lat));
    }
}
