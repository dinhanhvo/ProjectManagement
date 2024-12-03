package com.vodinh.prime.mappers;

import com.vodinh.prime.entities.Line;
import com.vodinh.prime.model.LineDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface LineMapper {

    @Mappings({
            @Mapping(source = "client.id", target = "clientId"), //, qualifiedByName = "convertClientIdToString"
            @Mapping(source = "client.username", target = "clientUsername"),
            @Mapping(source = "client.name", target = "clientName")
    })
    LineDTO toDTO(Line line);

    @Mappings({
            @Mapping(target = "client", ignore = true) // Không map `client`, xử lý trong service.
    })
    Line toEntity(LineDTO lineDTO);

    // Tùy chọn: Định nghĩa converter nếu cần thiết
//    default String convertClientIdToString(Long id) {
//        return id != null ? String.valueOf(id) : null;
//    }
}
