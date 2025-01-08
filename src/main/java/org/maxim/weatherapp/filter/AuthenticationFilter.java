package org.maxim.weatherapp.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.maxim.weatherapp.services.SessionService;
import org.maxim.weatherapp.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    private final SessionService sessionService;

    @Autowired
    public AuthenticationFilter(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();

        if (path.endsWith("/login") || path.endsWith("/registration")) {
            chain.doFilter(request, response);
            return;
        }

        String sessionId = CookieUtils.findCookieByName(httpRequest, "sessionId");

        if (sessionId.isEmpty()) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        } else if (!sessionService.isSessionActive(sessionId)) {
            sessionService.deleteSessionById(sessionId);
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }
        chain.doFilter(request, response);
    }
}
