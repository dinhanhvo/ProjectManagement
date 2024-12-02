package com.vodinh.prime.repositories;

import com.vodinh.prime.entities.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LineRepository extends JpaRepository<Line, Long>, JpaSpecificationExecutor<Line> {
    // Các phương thức tùy chỉnh nếu cần

    Optional<Line> findByLineId(String lineId);

    // Tìm kiếm theo status, lineId và name
    List<Line> findByStatusAndLineIdAndName(Boolean status, String lineId, String name);

    // Tìm kiếm theo status và lineId
    List<Line> findByStatusAndLineId(Boolean status, String lineId);

    // Tìm kiếm theo status và name
    List<Line> findByStatusAndName(Boolean status, String name);

    // Tìm kiếm theo lineId và name
    List<Line> findByLineIdAndName(String lineId, String name);

    // Tìm kiếm theo status
    List<Line> findByStatus(Boolean status);

    // Tìm kiếm theo name
    List<Line> findByName(String name);
}

