package ru.peregruzochka.travel_guide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.peregruzochka.travel_guide.model.City;

import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<City, UUID> {
}
