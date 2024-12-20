package org.maxim.weatherapp;

import java.io.*;
import java.util.Collection;
import java.util.Collections;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.flywaydb.core.Flyway;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
        try {
            Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://127.0.0.1:5432/weatherApp", "postgres", "root").load();
            flyway.migrate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void destroy() {
//        Collections collections = new Collection<Integer>();
    }
}