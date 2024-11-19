package com.vodinh.prime.service;

import com.vodinh.prime.entities.Project;
import com.vodinh.prime.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.orElse(null);
    }

    public Project createProject(Project project) {
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project project) {
        if (projectRepository.existsById(id)) {
            project.setId(id);
            project.setUpdatedAt(LocalDateTime.now());
            return projectRepository.save(project);
        }
        return null;
    }

    public boolean deleteProject(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
