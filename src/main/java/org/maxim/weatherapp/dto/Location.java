package org.maxim.weatherapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private int id;
    private String name;
    private int userId;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
