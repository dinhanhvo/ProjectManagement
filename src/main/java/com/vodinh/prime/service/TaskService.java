package com.vodinh.prime.service;

import com.vodinh.prime.entities.Project;
import com.vodinh.prime.entities.Task;
import com.vodinh.prime.enums.TaskStatus;
import com.vodinh.prime.exception.ResourceNotFoundException;
import com.vodinh.prime.mappers.TaskMapper;
import com.vodinh.prime.model.TaskDTO;
import com.vodinh.prime.repositories.ProjectRepository;
import com.vodinh.prime.repositories.TaskRepository;
import com.vodinh.prime.repositories.UserRepository;
import com.vodinh.prime.requests.TaskRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByProjectId() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Task getTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.orElse(null);
    }

    public TaskDTO createTask(TaskRequest taskRequest) {
        // create a task by builder pattern
        Task task = Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .status(TaskStatus.fromValue(taskRequest.getStatus()))
                .build();

        return this.upsertTask( taskRequest, task);
    }

    public TaskDTO updateTask(Long taskId, TaskRequest taskRequest) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("Task not found")
        );
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(TaskStatus.fromValue(taskRequest.getStatus()));

        return this.upsertTask(taskRequest, task);
    }

    public TaskDTO upsertTask(TaskRequest taskRequest, Task task) {
        // set status
        task.setStatus(TaskStatus.fromValue(taskRequest.getStatus()));

        // set belong to project which not existed will response ResourceNotFoundException
        Project project = projectRepository.findById(taskRequest.getProject()).orElseThrow(
                () -> new ResourceNotFoundException("Project not found")
        );
        task.setProject(project);

        // assign to user if any
        userRepository.findById(taskRequest.getAssignedTo()).ifPresent(task::setAssignedTo);

        Task savedTask = taskRepository.save(task);

        // convert to DTO
        return taskMapper.toDTO(savedTask);
    }

    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
