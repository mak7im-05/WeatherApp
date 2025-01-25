package org.maxim.weatherApp.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.maxim.weatherApp.service.SessionService;
import org.maxim.weatherApp.util.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final SessionService sessionService;

    @Autowired
    public AuthenticationInterceptor(@Lazy SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sessionId = CookieUtils.findCookieByName(request, "sessionId");
        if (sessionId == null || sessionId.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        try {
            UUID sessionUuid = UUID.fromString(sessionId);
            if (!sessionService.isSessionActive(sessionUuid)) {
                sessionService.deleteSessionById(sessionUuid);
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            }
            int userId = sessionService.getUserIdBySessionId(sessionUuid);
            request.setAttribute("userId", userId);
            return true;

        } catch (IllegalArgumentException e) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
    }
}