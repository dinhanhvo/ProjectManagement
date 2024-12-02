package com.vodinh.prime.mappers;

import com.vodinh.prime.entities.Weight;
import com.vodinh.prime.model.WeightDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WeightMapper {

    @Mapping(source = "user.id", target = "contactId")
    @Mapping(source = "user.username", target = "contactUsername")
    @Mapping(source = "user.name", target = "contactName")
    WeightDTO toDTO(Weight weight);

    @Mapping(source = "contactId", target = "user.id")
    @Mapping(source = "sellAt", target = "sellAt", defaultExpression = "java(java.time.LocalDateTime.now())")
    Weight toEntity(WeightDTO weightDTO);
}
