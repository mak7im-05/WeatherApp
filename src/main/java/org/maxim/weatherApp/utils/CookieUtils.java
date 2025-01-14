package org.maxim.weatherApp.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
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

    public static Cookie createCookieWithSessionId(UUID sessionId) {
        Cookie cookie = new Cookie("sessionId", sessionId.toString());
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }
}
