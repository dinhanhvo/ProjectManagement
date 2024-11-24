package com.vodinh.prime.mappers;

import org.mapstruct.MappingTarget;

import java.util.List;

public interface BaseMapper<E, D> {

    // Entity -> DTO
    D toDTO(E entity);

    // DTO -> Entity
    E toEntity(D dto);

    // List<Entity> -> List<DTO>
    List<D> toDTOList(List<E> entities);

    // List<DTO> -> List<Entity>
    List<E> toEntityList(List<D> dtos);

    // Update entity
//    void updateEntityFromDTO(D dto, @MappingTarget E entity);
}
