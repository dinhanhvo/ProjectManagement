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
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProjectService {


    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    private final RedissonClient redissonClient;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository,
                          ProjectMapper projectMapper, RedissonClient redissonClient) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectMapper = projectMapper;
        this.redissonClient = redissonClient;
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

    @Cacheable(value = "projects", key = "#projectId ")
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

        // Map all tasks into TaskDTO and add them into the project
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

    public List<ProjectDTO> searchProjectsByName(String name) {
        // get from Redisson first
        RMap<String, Project> projectMap = redissonClient.getMap("projects");
        List<Project> projects = new ArrayList<>();
        if (projectMap.isExists()) {
            projects = projectMap.values().stream()
                .filter(project -> project.getName().toLowerCase()
                .contains(name.toLowerCase())).collect(Collectors.toList());

            // if not cached yet, get from database
            if (projects.isEmpty()) {
                projects = this.projectRepository.findByName(name);
                // update cache
                Map<Long, Project> map = projects.stream().collect(Collectors.toMap(Project::getId, Function.identity()));
                RMap<Long, Project> projectCache = redissonClient.getMap("projects");
                projectCache.putAll(map);
            }
        }
        return projects.stream().map(this.projectMapper::toDTO).toList();

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
    @CacheEvict(value = "projects", key = "#projectId ")
    public ProjectDTO updateProject(Long projectId, ProjectDTO projectDTO) {
        if (projectRepository.existsById(projectId)) {
            Project prj = projectRepository.findById(projectId).get();
            prj.setName(projectDTO.getName());
            prj.setDescription(projectDTO.getDescription());
            Project ret = projectRepository.save(prj);
            projectDTO.setUpdatedAt(ret.getUpdatedAt());
            return projectDTO;
        }
        return null;
    }

    @Transactional
    @CacheEvict(value = "projects", key = "#projectId ")
    public boolean deleteProject(Long projectId) {
        if (projectRepository.existsById(projectId)) {
            projectRepository.deleteById(projectId);
            return true;
        }
        return false;
    }

}
