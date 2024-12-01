package com.vodinh.prime.repositories;

import com.vodinh.prime.entities.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeightRepository extends JpaRepository<Weight, Long> {
    List<Weight> findByUserId(Long user_id);

    List<Weight> findBySerialNumber(String seriNumber);

    List<Weight> findByModel(String model);

    Optional<Weight> findById(Long id);
}
