package org.maxim.weatherapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class firstContr {
    @GetMapping("/auth")
    public String getPage() {
        return "authentication.html";
    }

    @GetMapping("/reg")
    public String getPage2() {
        return "registration.html";
    }
}
