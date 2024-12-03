package com.vodinh.prime.mappers;

import com.vodinh.prime.entities.Weight;
import com.vodinh.prime.model.LineDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LineMapper {

    @Mapping(source = "user.id", target = "clientId")
    @Mapping(source = "user.username", target = "clientUsername")
    @Mapping(source = "user.name", target = "clientName")
    LineDTO toDTO(Weight weight);

    @Mapping(source = "clientId", target = "user.id")
    Weight toEntity(LineDTO lineDTO);
}
