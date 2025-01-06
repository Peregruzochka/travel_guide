package ru.peregruzochka.travel_guide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.peregruzochka.travel_guide.model.Sight;

import java.util.UUID;

@Repository
public interface SightRepository extends JpaRepository<Sight, UUID>, JpaSpecificationExecutor<Sight> {


}
