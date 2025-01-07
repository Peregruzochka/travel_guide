package ru.peregruzochka.travel_guide.specification;

import jakarta.persistence.criteria.Expression;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.domain.Specification;
import ru.peregruzochka.travel_guide.model.Sight;
import ru.peregruzochka.travel_guide.model.SightType;

import java.util.List;


public class SightSpecification {
    public static Specification<Sight> inArea(Point userLocation, int radius) {
        return ((root, query, criteriaBuilder) -> {
            Expression<Double> distance = criteriaBuilder.function(
                    "ST_DistanceSphere",
                    Double.class,
                    root.get("location"),
                    criteriaBuilder.literal(userLocation)
            );

            return criteriaBuilder.lessThanOrEqualTo(distance, (double) radius);
        });
    }

    public static Specification<Sight> moreThenAvgGrade(Float avgGrade) {
        if (avgGrade != null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("avgGrade"), avgGrade);
        }
        return null;
    }

    public static Specification<Sight> withTypes(List<SightType> sightTypes) {
        if (sightTypes != null && !sightTypes.isEmpty()) {
            return (root, query, criteriaBuilder) -> root.get("type").in(sightTypes);
        }
        return null;
    }

    public static Specification<Sight> sortedByDistance(Point userLocation) {
        return (root, query, criteriaBuilder) -> {
            Expression<Double> distance = criteriaBuilder.function(
                    "ST_Distance",
                    Double.class,
                    root.get("location"),
                    criteriaBuilder.literal(userLocation)
            );

            query.orderBy(criteriaBuilder.asc(distance));
            return criteriaBuilder.conjunction();
        };
    }
}
