package com.vodinh.prime.controller;

import com.vodinh.prime.entities.Project;
import com.vodinh.prime.model.ProjectDTO;
import com.vodinh.prime.model.ProjectWithTaskDTO;
import com.vodinh.prime.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // get all project
    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        logger.info("-------------- call API getAllProjects -----------");
        logger.warn("-------------- call API getAllProjects -----------");
        logger.info("-------------- call API getAllProjects -----------");
        List<ProjectDTO> projects = projectService.getAllProjects();
        logger.info("-------------- call API getAllProjects ----------- res: {}", projects);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // get by ID
    @GetMapping("/project/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        logger.info("-------------- call API getProjectById ----------- id: {}", id);
        ProjectDTO project = projectService.getProjectById(id);
        logger.info("-------------- call API getProjectById ----------- id: res: {}", project);
        return project != null ? new ResponseEntity<>(project, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/project/detail/{id}")
    public ResponseEntity<ProjectWithTaskDTO> getProjectWithTasks(@PathVariable Long id) {
        logger.info("-------------- call API getProjectWithTasks ----------- id: {}", id);
        ProjectWithTaskDTO result = projectService.getProjectWithTasks(id);
        logger.info("-------------- call API getProjectWithTasks ----------- res: {}", result);
        return result != null ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/projects/search")
    public ResponseEntity<List<ProjectDTO>> searchProjects(@RequestParam String name) {
        logger.info("-------------- call API searchProjects ----------- name: {}", name);
        List<ProjectDTO> projects = projectService.searchProjectsByName(name);
        logger.info("-------------- call API searchProjects ----------- res: {}", projects);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // create new
    @PostMapping("/project")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO project) {
        logger.info("-------------- call API createProject ----------- request: {}", project);
        ProjectDTO createdProject = projectService.createProject(project);
        logger.info("-------------- call API createProject ----------- res: {}", createdProject);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    // update project by id
    @PutMapping("/project/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id, @RequestBody ProjectDTO project) {
        logger.info("-------------- call API searchProjects ----------- project: {}", project);
        ProjectDTO updatedProject = projectService.updateProject(id, project);
        logger.info("-------------- call API searchProjects ----------- updatedProject: {}", updatedProject);
        return Objects.nonNull(updatedProject) ? new ResponseEntity<>(updatedProject, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // delete project by id
    @DeleteMapping("/project/{id}")
    public ResponseEntity<Boolean> deleteProject(@PathVariable Long id) {
        logger.info("-------------- call API deleteProject ----------- id: {}", id);
        boolean deleted = projectService.deleteProject(id);
        return deleted ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }
}
