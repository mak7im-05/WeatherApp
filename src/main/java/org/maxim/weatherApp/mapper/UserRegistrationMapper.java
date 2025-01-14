package org.maxim.weatherApp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.maxim.weatherApp.dto.RegisterRequestDTO;
import org.maxim.weatherApp.dto.UserServiceDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserRegistrationMapper {
    UserServiceDTO mapTo(RegisterRequestDTO source);
}
