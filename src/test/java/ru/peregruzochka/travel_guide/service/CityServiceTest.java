package ru.peregruzochka.travel_guide.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.peregruzochka.travel_guide.model.City;
import ru.peregruzochka.travel_guide.repository.CityRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {
    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    @Test
    public void createCity() {
        cityService.createCity("Moscow");
        verify(cityRepository, times(1)).save(any(City.class));
    }
}
