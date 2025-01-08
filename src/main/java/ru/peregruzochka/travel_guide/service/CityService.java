package ru.peregruzochka.travel_guide.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.peregruzochka.travel_guide.model.City;
import ru.peregruzochka.travel_guide.repository.CityRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    @Transactional
    public City createCity(String cityName) {
        City city = City.builder()
                .cityName(cityName)
                .build();
        City savedCity = cityRepository.save(city);
        log.info("Saved city: {}", savedCity);
        return savedCity;
    }
}
