package com.vodinh.prime.repositories;

import com.vodinh.prime.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByName(String name);
}