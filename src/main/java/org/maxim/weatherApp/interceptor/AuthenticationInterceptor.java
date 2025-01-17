package org.maxim.weatherApp.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.maxim.weatherApp.services.SessionService;
import org.maxim.weatherApp.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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

        if (!sessionService.isSessionActive(sessionId)) {
            sessionService.deleteSessionById(sessionId);
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        int userId = sessionService.getUserIdBySessionId(sessionId);
        request.setAttribute("userId", userId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}