package org.maxim.weatherApp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.maxim.weatherApp.dto.request.UserServiceRequestDTO;
import org.maxim.weatherApp.entities.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserEntityMapper {
    User mapTo(UserServiceRequestDTO source);
}
