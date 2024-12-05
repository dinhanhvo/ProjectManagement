package com.vodinh.prime.mappers;

import com.vodinh.prime.entities.Factory;
import com.vodinh.prime.model.FactoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FactoryMapper extends BaseMapper<Factory, FactoryDTO> {

    @Mapping(source = "region.regionId", target = "regionId")
    @Mapping(source = "region.name", target = "regionName")
    FactoryDTO toDTO(Factory entity);

    @Mapping(target = "region", ignore = true)
    Factory toEntity(FactoryDTO dto);
}
