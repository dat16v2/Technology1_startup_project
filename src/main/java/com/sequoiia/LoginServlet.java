package com.sequoiia;

import com.sequoiia.blackexercise.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Logger;

class UserPass {
    public String Username;
    public String Password;

    UserPass(String username, String password) {
        Username = username;
        Password = password;
    }
}

public class LoginServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(LoginServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        response.setHeader("Content-Type", "application/json");

        HashMap<String, User> users = User.getUsers("WEB-INF/users.txt");


        if (users.containsKey(username.toLowerCase())) {
            if (users.get(username.toLowerCase()).Password.equals(password)) {
                Cookie userCookie = new Cookie("username", username.toLowerCase());
                userCookie.setPath("/");
                Cookie timestampCookie = new Cookie("timestamp", Long.toString(System.currentTimeMillis()));
                timestampCookie.setPath("/");
                Cookie statusCookie = new Cookie("status", "1");
                statusCookie.setPath("/");

                response.addCookie(userCookie);
                response.addCookie(timestampCookie);
                response.addCookie(statusCookie);

                response.getWriter().println("{\"status\": true}");
            } else {
                response.getWriter().println("{\"status\": false}");
            }
        } else {
            response.getWriter().println("{\"status\": false}");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        //response.getWriter().println(System.getProperty("user.dir"));

        PrintWriter out = response.getWriter();
        response.setStatus(200);

        out.println("<center><h1>Wrong page mate. <a href=\"/\">Click here</a></h1></center>");
    }

}