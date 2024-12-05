package com.vodinh.prime.repositories;

import com.vodinh.prime.entities.Factory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FactoryRepository extends JpaRepository<Factory, Long> {
}
