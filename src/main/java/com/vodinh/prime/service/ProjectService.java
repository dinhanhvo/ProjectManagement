package com.vodinh.prime.service;

import com.vodinh.prime.entities.Project;
import com.vodinh.prime.entities.User;
import com.vodinh.prime.exception.ResourceNotFoundException;
import com.vodinh.prime.mappers.ProjectMapper;
import com.vodinh.prime.model.ProjectDTO;
import com.vodinh.prime.model.ProjectWithTaskDTO;
import com.vodinh.prime.model.TaskDTO;
import com.vodinh.prime.repositories.ProjectRepository;
import com.vodinh.prime.repositories.UserRepository;
import com.vodinh.prime.util.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {


    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectMapper = projectMapper;
    }

    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + id));
        return projectMapper.toDTO(project);
    }

    public ProjectWithTaskDTO getProjectWithTasks(Long projectId) {
        List<Object[]> results = projectRepository.getProjectWithTasksNative(projectId);
        if (results.isEmpty()) {
            throw new EntityNotFoundException("Project not found for ID: " + projectId);
        }

        // Map the project details from the first row
        Object[] firstRow = results.getFirst();
        ProjectWithTaskDTO projectWithTaskDTO = new ProjectWithTaskDTO(
                (Long) firstRow[0],   // projectId
                (String) firstRow[1], // projectName
                (String) firstRow[2], // ownerName
                (String) firstRow[3], // projectDescription
                ((Timestamp) firstRow[4]).toLocalDateTime(), // projectCreatedAt
                ((Timestamp) firstRow[5]).toLocalDateTime()  // projectUpdatedAt
        );

        // Map all tasks into TaskDTO and add them to the project
        for (Object[] row : results) {
            if (row[6] != null) { // Check if task exists
                TaskDTO taskDTO = TaskDTO.builder()
                        .id((Long) row[6])
                        .title((String) row[7])
                        .project(projectWithTaskDTO.getName())
                        .description((String) row[8])
                        .assignedTo((String) row[9])
                        .status((String) row[10])
                        .createdAt(((Timestamp) firstRow[11]).toLocalDateTime())
                        .createdAt(((Timestamp) firstRow[12]).toLocalDateTime())
                        .build();
                projectWithTaskDTO.getTaskDTOS().add(taskDTO);
            }
        }
        return projectWithTaskDTO;
    }

    @Transactional
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        String username = SecurityUtils.getCurrentUser();
        User user = userRepository.findByUsername(SecurityUtils.getCurrentUser()).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)
        );
        Project project = projectMapper.toEntity(projectDTO);
        project.setOwner(user);
        project.setCreatedBy(user.getId());
        project.setUpdatedBy(user.getId());
        projectRepository.save(project);
        return projectMapper.toDTO(project);
    }

    @Transactional
    public ProjectDTO updateProject(Long id, ProjectDTO projectDTO) {
        if (projectRepository.existsById(id)) {
            Project prj = projectRepository.findById(id).get();
            prj.setName(projectDTO.getName());
            prj.setDescription(projectDTO.getDescription());
            Project ret = projectRepository.save(prj);
            projectDTO.setUpdatedAt(ret.getUpdatedAt());
            return projectDTO;
        }
        return null;
    }

    @Transactional
    public boolean deleteProject(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ProjectWithTaskDTO mapToDTO(Object[] result) {
        Long projectId = (Long) result[0];
        String projectName = (String) result[1];
        String ownerName = (String) result[2]; // Retrieved from the `user` table
        String description = (String) result[3];
        LocalDateTime createdAt = (LocalDateTime) result[4];
        LocalDateTime updatedAt = (LocalDateTime) result[5];

        // Initialize ProjectWithTaskDTO
        ProjectWithTaskDTO dto = new ProjectWithTaskDTO(projectId, projectName, ownerName, description, createdAt, updatedAt);

        // Task details, if present
        if (result[6] != null) {
            TaskDTO taskDTO = new TaskDTO(
                    (Long) result[6],  // Task ID
                    (String) result[7], // Task Title
                    projectName,        // Project Name
                    (String) result[8], // Task Description
                    (String) result[9], // Assigned To
                    (String) result[10],// Status
                    (LocalDateTime) result[11], // Created At
                    (LocalDateTime) result[12]  // Updated At
            );
            dto.getTaskDTOS().add(taskDTO);
        }

        return dto;
    }


}
