package com.vodinh.prime.mappers;

import com.vodinh.prime.entities.Region;
import com.vodinh.prime.model.RegionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegionMapper extends BaseMapper<Region, RegionDTO> {
}
