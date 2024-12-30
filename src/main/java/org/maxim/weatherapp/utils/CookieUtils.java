package org.maxim.weatherapp.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CookieUtils {
    public static String findCookieByName(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null) return null;
        String sessionId = "";
        for (Cookie c : cookies) {
            if (c.getName().equals(name)) {
                sessionId = c.getValue();
                break;
            }
        }
        return sessionId;
    }

    public static Cookie createCookieWithSessionId(String sessionId) {
        Cookie cookie = new Cookie("sessionId", sessionId);
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }
}
