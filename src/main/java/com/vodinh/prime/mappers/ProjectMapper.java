package com.vodinh.prime.mappers;

import com.vodinh.prime.entities.Project;
import com.vodinh.prime.model.ProjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(source = "owner.username", target = "owner")
    ProjectDTO toDTO(Project project);

    @Mapping(source = "owner", target = "owner.username")
    Project toEntity(ProjectDTO projectDTO);
}
