package org.maxim.weatherapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserServiceDTO (
        @NotEmpty(message = "login should not be empty")
        @Email(message = "email should be valid")
        String login,
        @NotEmpty(message = "password should not be empty")
        @Size(min = 8, max = 64, message = "password size should be more 8 character and less 64 character")
        String password) {
}
