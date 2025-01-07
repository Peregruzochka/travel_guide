package ru.peregruzochka.travel_guide.service;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.peregruzochka.travel_guide.controller.SortedType;
import ru.peregruzochka.travel_guide.dto.SightFilterDto;
import ru.peregruzochka.travel_guide.model.City;
import ru.peregruzochka.travel_guide.model.Sight;
import ru.peregruzochka.travel_guide.repository.CityRepository;
import ru.peregruzochka.travel_guide.repository.SightRepository;
import ru.peregruzochka.travel_guide.specification.SightSpecification;

import java.util.List;
import java.util.UUID;

import static ru.peregruzochka.travel_guide.controller.SortedType.LOCATION;

@Service
@RequiredArgsConstructor
public class SightService {
    private final SightRepository sightRepository;
    private final CityRepository cityRepository;

    @Transactional(readOnly = true)
    public List<Sight> getNearSortedSightByFilter(double lat, double lon, int searchRadius, int size,
                                                  SortedType sortedType,
                                                  SightFilterDto sightFilterDto) {

        PageRequest pageRequest = PageRequest.ofSize(size);
        if (!sortedType.equals(LOCATION)) {
            pageRequest = PageRequest.of(1, size, Sort.by(Sort.Direction.DESC, sortedType.getValue()));
        }

        GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);
        Point userLocation = factory.createPoint(new Coordinate(lon, lat));

        Specification<Sight> spec = Specification.where(SightSpecification.inArea(userLocation, searchRadius));
        if (sightFilterDto != null) {
            spec.and(SightSpecification.moreThenAvgGrade(sightFilterDto.getAverageGrade()))
                    .and(SightSpecification.withTypes(sightFilterDto.getTypes()));
        }

        List<Sight> sights = sightRepository.findAll(spec, pageRequest).getContent();
        return sights;
    }

    @Transactional
    public Sight createSight(Sight sight) {
        UUID cityId = sight.getCity().getId();
        City city = cityRepository.findById(cityId).orElseThrow();
        sight.setCity(city);
        sight.setAvgGrade(0.0f);
        Sight savedSight = sightRepository.save(sight);
        return savedSight;
    }
}
