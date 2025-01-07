package ru.peregruzochka.travel_guide.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.peregruzochka.travel_guide.dto.CityDto;
import ru.peregruzochka.travel_guide.mapper.CityMapper;
import ru.peregruzochka.travel_guide.model.City;
import ru.peregruzochka.travel_guide.service.CityService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/city")
public class CityController {
    private final CityService cityService;
    private final CityMapper cityMapper;

    @PostMapping
    public CityDto addCity(@RequestParam(name = "city-name") String cityName) {
        City savedCity = cityService.createCity(cityName);
        return cityMapper.toCityDto(savedCity);
    }
}
