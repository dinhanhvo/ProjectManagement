package com.vodinh.prime.util;

import com.vodinh.prime.entities.Line;
import org.springframework.data.jpa.domain.Specification;

public class LineSpecification {

    public static Specification<Line> hasStatus(Boolean status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction(); // không có điều kiện
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Line> hasLineId(String lineId) {
        return (root, query, criteriaBuilder) -> {
            if (lineId == null) {
                return criteriaBuilder.conjunction(); // không có điều kiện
            }
            return criteriaBuilder.equal(root.get("lineId"), lineId);
        };
    }

    public static Specification<Line> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null) {
                return criteriaBuilder.conjunction(); // không có điều kiện
            }
            return criteriaBuilder.like(root.get("name"), "%" + name + "%");
        };
    }
    public static Specification<Line> hasClientId(Long clientId) {
        return (root, query, criteriaBuilder) -> {
            if (clientId == null) {
                return criteriaBuilder.conjunction(); // không có điều kiện
            }
            return criteriaBuilder.equal(root.get("client").get("id"), clientId);
        };
    }
}
