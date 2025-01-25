package org.maxim.weatherApp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.maxim.weatherApp.dto.request.RegisterRequestDTO;
import org.maxim.weatherApp.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserRegistrationMapper {
    User toServiceDto(RegisterRequestDTO source);
}
