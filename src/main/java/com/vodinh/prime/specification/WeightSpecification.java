package com.vodinh.prime.specification;

import com.vodinh.prime.entities.Weight;
import com.vodinh.prime.enums.WeightStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WeightSpecification {

    public static Specification<Weight> search(String serialNumber, Long contactId,
                                               Long lineId, WeightStatus weightStatus,
                                               LocalDateTime fromSellAt, LocalDateTime toSellAt) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Tham số serial_number
            if (serialNumber != null && !serialNumber.isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("serialNumber")),
                        "%" + serialNumber.toLowerCase() + "%"
                ));
            }

            // Tham số contact_id
            if (contactId != null) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), contactId));
            }

            // Tham số line_id
            if (lineId != null) {
                predicates.add(criteriaBuilder.equal(root.get("line").get("id"), lineId));
            }

            if (weightStatus != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), weightStatus));
            }

            // Tham số sell_at (from ... to)
            if (fromSellAt != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("sellAt"), fromSellAt));
            }

            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("sellAt"), toSellAt));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
