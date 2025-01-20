package org.maxim.weatherApp.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class CookieUtils {
    public String findCookieByName(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        String sessionId = "";
        for (Cookie c : cookies) {
            if (c.getName().equals(name)) {
                sessionId = c.getValue();
                break;
            }
        }
        return sessionId;
    }

    public Cookie createCookieWithSessionId(UUID sessionId) {
        Cookie cookie = new Cookie("sessionId", sessionId.toString());
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }

    public static void deleteCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
