package ru.peregruzochka.travel_guide.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import ru.peregruzochka.travel_guide.controller.SortedType;
import ru.peregruzochka.travel_guide.dto.SightFilterDto;
import ru.peregruzochka.travel_guide.model.City;
import ru.peregruzochka.travel_guide.model.Sight;
import ru.peregruzochka.travel_guide.repository.CityRepository;
import ru.peregruzochka.travel_guide.repository.SightRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SightServiceTest {

    @Mock
    private SightRepository sightRepository;

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private SightService sightService;

    private City city;
    private Sight sight;

    @BeforeEach
    void setUp() {
        city = new City();
        city.setId(UUID.randomUUID());

        sight = new Sight();
        sight.setId(UUID.randomUUID());
        sight.setCity(city);
        sight.setAvgGrade(4.0f);
    }

    @Test
    void testCreateSight() {
        when(cityRepository.findById(city.getId())).thenReturn(Optional.of(city));
        when(sightRepository.save(any(Sight.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Sight savedSight = sightService.createSight(sight);

        assertNotNull(savedSight);
        assertEquals(city, savedSight.getCity());
        assertEquals(0.0f, savedSight.getAvgGrade());

        verify(cityRepository).findById(city.getId());
        verify(sightRepository).save(sight);
    }

    @Test
    void testGetNearSightByOrderAndFilters() {
        double lat = 55.7558;
        double lon = 37.6173;
        int searchRadius = 5000;
        SortedType sortedType = SortedType.DISTANCE;
        SightFilterDto filterDto = new SightFilterDto();
        filterDto.setAverageGrade(4.0f);
        filterDto.setTypes(Collections.emptyList());
        int limit = 10;

        Page<Sight> sightPage = new PageImpl<>(Collections.singletonList(sight), PageRequest.ofSize(limit), 1);
        when(sightRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(sightPage);

        List<Sight> sights = sightService.getNearSightByOrderAndFilters(lat, lon, searchRadius, sortedType, filterDto, limit);

        assertNotNull(sights);
        assertEquals(1, sights.size());
        assertEquals(sight, sights.get(0));

        verify(sightRepository).findAll(any(Specification.class), any(PageRequest.class));
    }

    @Test
    void testGetCitySightByOrderAndFilters() {
        UUID cityId = city.getId();
        SortedType sortType = SortedType.GRADE;
        SightFilterDto filterDto = new SightFilterDto();
        filterDto.setAverageGrade(4.0f);
        filterDto.setTypes(Collections.emptyList());
        int limit = 10;

        Page<Sight> sightPage = new PageImpl<>(Collections.singletonList(sight), PageRequest.ofSize(limit), 1);
        when(sightRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(sightPage);

        List<Sight> sights = sightService.getCitySightByOrderAndFilters(cityId, sortType, filterDto, limit);

        assertNotNull(sights);
        assertEquals(1, sights.size());
        assertEquals(sight, sights.get(0));

        verify(sightRepository).findAll(any(Specification.class), any(PageRequest.class));
    }

    @Test
    void testGetSightById() {
        UUID sightId = sight.getId();

        when(sightRepository.findById(sightId)).thenReturn(Optional.of(sight));

        Sight foundSight = sightService.getSightById(sightId);

        assertNotNull(foundSight);
        assertEquals(sight, foundSight);

        verify(sightRepository).findById(sightId);
    }

    @Test
    void testGetSightById_NotFound() {
        UUID sightId = UUID.randomUUID();

        when(sightRepository.findById(sightId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> sightService.getSightById(sightId));

        verify(sightRepository).findById(sightId);
    }
}

