package ru.peregruzochka.travel_guide.specification;

import jakarta.persistence.criteria.Expression;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.domain.Specification;
import ru.peregruzochka.travel_guide.model.Sight;
import ru.peregruzochka.travel_guide.model.SightType;

import java.util.List;


public class SightSpecification {
    public static Specification<Sight> inArea(double lat, double lon, int radius) {
        return ((root, query, criteriaBuilder) -> {
            Expression<?> point = criteriaBuilder.function(
                    "ST_SetSRID",
                    Point.class,
                    criteriaBuilder.function("ST_MakePoint", Double.class, criteriaBuilder.literal(lon), criteriaBuilder.literal(lat)),
                    criteriaBuilder.literal(4326)
            );

            Expression<Boolean> within = criteriaBuilder.function(
                    "ST_DWithin",
                    Boolean.class,
                    root.get("geom"),
                    point,
                    criteriaBuilder.literal(radius)
            );

            return criteriaBuilder.isTrue(within);
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

}
