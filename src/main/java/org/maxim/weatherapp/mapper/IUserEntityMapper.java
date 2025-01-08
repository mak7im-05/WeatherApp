package org.maxim.weatherapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.maxim.weatherapp.entities.User;
import org.maxim.weatherapp.dto.UserServiceDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IUserEntityMapper {
    User mapTo(UserServiceDTO source);
    UserServiceDTO mapFrom(User target);
}
