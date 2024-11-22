package org.maxim.weatherapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class Session {
    private UUID id;
    private int userId;
    private int expiresAt;
}
