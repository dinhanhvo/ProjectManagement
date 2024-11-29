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
        logger.info("This is an INFO log message");
        logger.warn("This is a WARN log message");
        logger.error("This is an ERROR log message");

        List<ProjectDTO> projects = projectService.getAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // get by ID
    @GetMapping("/project/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        ProjectDTO project = projectService.getProjectById(id);
        return project != null ? new ResponseEntity<>(project, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/project/detail/{id}")
    public ResponseEntity<ProjectWithTaskDTO> getProjectWithTasks(@PathVariable Long id) {
        ProjectWithTaskDTO result = projectService.getProjectWithTasks(id);
        return result != null ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/projects/search")
    public ResponseEntity<List<ProjectDTO>> searchProjects(@RequestParam String name) {
        List<ProjectDTO> projects = projectService.searchProjectsByName(name);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // create new
    @PostMapping("/project")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO project) {
        ProjectDTO createdProject = projectService.createProject(project);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    // update project by id
    @PutMapping("/project/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id, @RequestBody ProjectDTO project) {
        ProjectDTO updatedProject = projectService.updateProject(id, project);
        return Objects.nonNull(updatedProject) ? new ResponseEntity<>(updatedProject, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // delete project by id
    @DeleteMapping("/project/{id}")
    public ResponseEntity<Boolean> deleteProject(@PathVariable Long id) {
        boolean deleted = projectService.deleteProject(id);
        return deleted ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }
}
