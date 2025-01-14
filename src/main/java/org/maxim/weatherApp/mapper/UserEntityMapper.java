package org.maxim.weatherApp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.maxim.weatherApp.entities.User;
import org.maxim.weatherApp.dto.UserServiceDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserEntityMapper {
    User mapTo(UserServiceDTO source);
}
