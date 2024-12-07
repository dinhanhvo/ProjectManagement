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
    @Mapping(source = "line.id", target = "lineId")
    @Mapping(source = "line.name", target = "lineName")
    @Mapping(source = "status", target = "status")
    WeightDTO toDTO(Weight weight);

//    @Mapping(source = "contactId", target = "user.id")
//    @Mapping(target = "line.id", source = "lineId", ignore = true)
    @Mapping(source = "status", target = "status")
    @Mapping(source = "sellAt", target = "sellAt", defaultExpression = "java(java.time.LocalDateTime.now())")
    Weight toEntity(WeightDTO weightDTO);
}
