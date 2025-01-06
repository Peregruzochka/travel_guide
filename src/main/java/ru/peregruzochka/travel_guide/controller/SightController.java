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

    @PostMapping
    public List<SightDto> getSights(@RequestParam double lat,
                                    @RequestParam double lon,
                                    @RequestParam(name = "radius") int searchRadius, //metres
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "LOCATION") SortedType sortedType,
                                    @RequestBody SightFilterDto sightFilterDto
                                    ) {
        List<Sight> sights = sightService.getNearSortedSightByFilter(lat, lon, searchRadius, size, sortedType, sightFilterDto);
        return sightMapper.toSightDtoList(sights);
    }
}
