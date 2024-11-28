package com.vodinh.prime.repositories;

import com.vodinh.prime.entities.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByName(String name);

    @EntityGraph(attributePaths = {"tasks"})
    Project findWithTasksById(Long id);

    @Query(value = "SELECT \n" +
            "        p.id AS projectId, \n" +
            "        p.name AS projectName, \n" +
            "        u.name AS ownerName, \n" +
            "        p.description AS projectDescription, \n" +
            "        p.created_at AS projectCreatedAt, \n" +
            "        p.updated_at AS projectUpdatedAt,\n" +
            "        t.id AS taskId, \n" +
            "        t.title AS taskTitle, \n" +
            "        t.description AS taskDescription, \n" +
            "        a.name AS assignedToName, \n" +
            "        t.status AS taskStatus, \n" +
            "        t.created_at AS taskCreatedAt, \n" +
            "        t.updated_at AS taskUpdatedAt\n" +
            "    FROM project p\n" +
            "    JOIN user u ON p.owner_id = u.id\n" +
            "    LEFT JOIN task t ON p.id = t.project_id\n" +
            "    LEFT JOIN user a ON t.assigned_to = a.id\n" +
            "    WHERE p.id = :projectId"
            , nativeQuery = true)
    List<Object[]> getProjectWithTasksNative(@Param("projectId") Long projectId);

}