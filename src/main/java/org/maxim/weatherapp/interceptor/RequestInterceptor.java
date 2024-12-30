package org.maxim.weatherapp.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.maxim.weatherapp.dao.SessionDao;
import org.maxim.weatherapp.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class RequestInterceptor implements HandlerInterceptor {
    @Autowired
    private SessionDao sessionDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String sessionId = CookieUtils.findCookieByName(request, "sessionId");
        if (sessionId == null || sessionId.isEmpty() || sessionDao.findSessionById(sessionId) == null) {
            response.sendRedirect("/login");
        }
        return true;
    }
}
