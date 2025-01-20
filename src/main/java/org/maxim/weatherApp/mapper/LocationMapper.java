package org.maxim.weatherApp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.maxim.weatherApp.dto.request.LocationRequestDto;
import org.maxim.weatherApp.entities.Location;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationMapper {
    Location mapTo(LocationRequestDto source);
}
