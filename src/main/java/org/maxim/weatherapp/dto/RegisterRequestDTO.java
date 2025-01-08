package org.maxim.weatherapp.dto;

public record RegisterRequestDTO(String login,
                                 String password,
                                 String confirmPassword) {
}