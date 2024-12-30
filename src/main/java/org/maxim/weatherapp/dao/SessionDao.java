package org.maxim.weatherapp.dao;

import org.maxim.weatherapp.dto.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SessionDao {
    public Session findSessionById(String sessionId) {
        return new Session();
    }
}
