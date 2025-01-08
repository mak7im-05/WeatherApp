package org.maxim.weatherapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempController {

    @GetMapping("/temp")
    public String showTempPage() {
        return "temp";
    }
}
