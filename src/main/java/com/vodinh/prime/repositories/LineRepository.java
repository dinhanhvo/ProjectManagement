package com.vodinh.prime.repositories;

import com.vodinh.prime.entities.Line;
import com.vodinh.prime.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LineRepository extends JpaRepository<Line, Long>, JpaSpecificationExecutor<Line> {

    Optional<Line> findByLineId(String lineId);
    Optional<Line> findByClientAndId(User user, Long id);

    List<Line> findByStatus(Boolean status);

    List<Line> findByName(String name);
}

