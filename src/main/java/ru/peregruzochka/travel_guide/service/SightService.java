package ru.peregruzochka.travel_guide.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.peregruzochka.travel_guide.controller.SortedType;
import ru.peregruzochka.travel_guide.dto.SightFilterDto;
import ru.peregruzochka.travel_guide.model.Sight;
import ru.peregruzochka.travel_guide.repository.SightRepository;
import ru.peregruzochka.travel_guide.specification.SightSpecification;

import java.util.List;

import static ru.peregruzochka.travel_guide.controller.SortedType.LOCATION;

@Service
@RequiredArgsConstructor
public class SightService {
    private final SightRepository sightRepository;

    @Transactional(readOnly = true)
    public List<Sight> getNearSortedSightByFilter(double lat, double lon, int searchRadius, int size,
                                           SortedType sortedType,
                                           SightFilterDto sightFilterDto) {

        PageRequest pageRequest = PageRequest.ofSize(size);
        if (!sortedType.equals(LOCATION)) {
            pageRequest = PageRequest.of(1, size, Sort.by(Sort.Order.by(sortedType.getValue())));
        }

        Specification<Sight> spec = Specification.where(SightSpecification.inArea(lat, lon, searchRadius))
                .and(SightSpecification.moreThenAvgGrade(sightFilterDto.getAverageGrade()))
                .and(SightSpecification.withTypes(sightFilterDto.getTypes()));

        List<Sight> sights = sightRepository.findAll(spec, pageRequest).getContent();
        return sights;
    }
}
