package org.maxim.weatherapp.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private int id;
    private String name;
    private int userId;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
