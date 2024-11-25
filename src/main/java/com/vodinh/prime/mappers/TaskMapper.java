package com.vodinh.prime.mappers;

import com.vodinh.prime.entities.Task;
import com.vodinh.prime.model.TaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "assignedTo.username", target = "assignedTo")
    @Mapping(source = "project.name", target = "project")
    TaskDTO toDTO(Task task);

    @Mapping(target = "project", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    Task toEntity(TaskDTO taskDTO);
}
