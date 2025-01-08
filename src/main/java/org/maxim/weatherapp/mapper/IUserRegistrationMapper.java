package org.maxim.weatherapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.maxim.weatherapp.dto.RegisterRequestDTO;
import org.maxim.weatherapp.dto.UserServiceDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IUserRegistrationMapper  {
    UserServiceDTO mapTo(RegisterRequestDTO source);
    RegisterRequestDTO mapFrom(UserServiceDTO target);
}
