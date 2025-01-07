package ru.peregruzochka.travel_guide.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.peregruzochka.travel_guide.dto.SightDto;
import ru.peregruzochka.travel_guide.dto.SightFilterDto;
import ru.peregruzochka.travel_guide.mapper.SightMapper;
import ru.peregruzochka.travel_guide.model.Sight;
import ru.peregruzochka.travel_guide.service.SightService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sights")
public class SightController {
    private final SightService sightService;
    private final SightMapper sightMapper;

    @PostMapping("/distance-search")
    public List<SightDto> getSights(@RequestParam double lat,
                                    @RequestParam double lon,
                                    @RequestParam(name = "radius", defaultValue = "10000") int searchRadius, //metres
                                    @RequestParam(name = "sort-type", defaultValue = "LOCATION") SortedType sortType,
                                    @RequestBody(required = false) SightFilterDto sightFilterDto,
                                    @RequestParam(defaultValue = "10") int limit
    ) {
        List<Sight> sights = sightService.getNearSightByOrderAndFilters(lat, lon, searchRadius, sortType, sightFilterDto, limit);
        return sightMapper.toSightDtoList(sights);
    }

    @PostMapping
    public SightDto createSight(@RequestBody SightDto sightDto) {
        Sight newSight = sightMapper.toSightEntity(sightDto);
        Sight savedSight = sightService.createSight(newSight);
        return sightMapper.toSightDto(savedSight);
    }
}
