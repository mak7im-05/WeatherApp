package org.maxim.weatherApp.dto.request;

import jakarta.validation.constraints.*;

public record RegisterRequestDTO(@NotBlank(message = "login should not be empty")
                                 @NotEmpty(message = "login should not be empty")
                                 @Email(message = "email should be valid")
                                 @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email should contain only English letters and digits")
                                 String login,
                                 @NotBlank(message = "password should not be empty")
                                 @NotEmpty(message = "password should not be empty")
                                 @Size(min = 8, max = 64, message = "password size should be more 8 character and less 64 character")
                                 String password,
                                 @NotBlank(message = "password should not be empty")
                                 @NotEmpty(message = "password should not be empty")
                                 @NotEmpty(message = "password should not be empty")
                                 @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,64}$", message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and be between 8 and 64 characters long.")
                                 String confirmPassword) {
}