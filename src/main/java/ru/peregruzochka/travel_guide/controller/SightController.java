package ru.peregruzochka.travel_guide.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sights")
public class SightController {
    private final SightService sightService;
    private final SightMapper sightMapper;

    @PostMapping
    public SightDto createSight(@RequestBody @Valid SightDto sightDto) {
        Sight newSight = sightMapper.toSightEntity(sightDto);
        Sight savedSight = sightService.createSight(newSight);
        return sightMapper.toSightDto(savedSight);
    }

    @PostMapping("/distance-search")
    public List<SightDto> getSights(@RequestParam double lat,
                                    @RequestParam double lon,
                                    @RequestParam(name = "radius", defaultValue = "10000") int searchRadius, //metres
                                    @RequestParam(name = "sort-type", defaultValue = "DISTANCE") SortedType sortType,
                                    @RequestBody(required = false) SightFilterDto sightFilterDto,
                                    @RequestParam(defaultValue = "10") int limit
    ) {
        List<Sight> sights = sightService.getNearSightByOrderAndFilters(lat, lon, searchRadius, sortType, sightFilterDto, limit);
        return sightMapper.toSightDtoList(sights);
    }

    @PostMapping("/city-search")
    public List<SightDto> getSight(@RequestParam(name = "city-id") UUID cityId,
                                   @RequestParam(name = "sort-type", defaultValue = "DISTANCE") SortedType sortType,
                                   @RequestBody(required = false) SightFilterDto sightFilterDto,
                                   @RequestParam(defaultValue = "10") int limit) {
        List<Sight> sights = sightService.getCitySightByOrderAndFilters(cityId, sortType, sightFilterDto, limit);
        return sightMapper.toSightDtoList(sights);
    }

    @GetMapping("/{sight-id}")
    public SightDto getSight(@PathVariable(name = "sight-id") UUID sightId) {
        Sight sight = sightService.getSightById(sightId);
        return sightMapper.toSightDto(sight);
    }
}
