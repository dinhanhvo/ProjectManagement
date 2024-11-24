package com.vodinh.prime.service;

import com.vodinh.prime.entities.Project;
import com.vodinh.prime.entities.User;
import com.vodinh.prime.exception.ResourceNotFoundException;
import com.vodinh.prime.mappers.ProjectMapper;
import com.vodinh.prime.model.ProjectDTO;
import com.vodinh.prime.repositories.ProjectRepository;
import com.vodinh.prime.repositories.UserRepository;
import com.vodinh.prime.util.SecurityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
