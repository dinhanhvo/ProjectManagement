package com.vodinh.prime.repositories;

import com.vodinh.prime.entities.Weight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeightRepository extends JpaRepository<Weight, Long>, JpaSpecificationExecutor<Weight> {
    Page<Weight> findByUserId(Pageable pageable, Long user_id);

    Optional<Weight> findBySerialNumber(String serialNumber);

    Optional<Weight> findByModel(String model);

}
