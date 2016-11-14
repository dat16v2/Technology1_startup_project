package com.faisal;

import com.sequoiia.blackexercise.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by faisaljarkass on 19/08/16.
 * mvn appengine:update
 * mvn appengine:devserver
 */
public class MyServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(MyServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.INFO, "doPost start...");
        logger.log(Level.INFO, "Username: " + request.getParameter("username"));
        logger.log(Level.INFO, "Password: " + request.getParameter("password"));
        logger.log(Level.INFO, "Checkbox: " + request.getParameter("rememberMe"));

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        String title = "Using POST Method to Read Form Data";
        String docType =
                "<!doctype html public \"-//w3c//dtd html 4.0 " +
                        "transitional//en\">\n";
        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + title + "</h1>\n" +
                "<ul>\n" +
                "  <li><b>Username</b>: "
                + request.getParameter("username") + "\n" +
                "  <li><b>Password</b>: "
                + request.getParameter("password") + "\n" +
                "</ul>\n" +
                "</body></html>");
        logger.log(Level.INFO, "doPost ended...");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.INFO, "doGet started...");
        response.setContentType("text/html");

        HashMap<String, User> users = User.getUsers("WEB-INF/users.txt", request);
        Cookie[] cookies = request.getCookies();
        String username = "";
        Long timestamp = (long) 0;
        String status = "";

        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("status")) {
                status = cookies[i].getValue();
            }
        }

        if (status.equals("1")) {

            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("username")) {
                    username = cookies[i].getValue();
                }

                if (cookies[i].getName().equals("timestamp")) {
                    timestamp = Long.parseLong(cookies[i].getValue());
                }
            }

            long timePlus5Mins = System.currentTimeMillis() - (long) ((1000 * 60) * 5);
            logger.log(Level.WARNING, "System time:" + System.currentTimeMillis() + ", 300k: " + (long) ((1000 * 60) * 5) + ", timestamp: " + timestamp);


            if (timePlus5Mins > timestamp) {
                Cookie userCookie = new Cookie("username", "");
                userCookie.setPath("/");
                Cookie timestampCookie = new Cookie("timestamp", "");
                timestampCookie.setPath("/");
                Cookie statusCookie = new Cookie("status", "0");
                statusCookie.setPath("/");

                response.addCookie(userCookie);
                response.addCookie(timestampCookie);
                response.addCookie(statusCookie);

                response.getWriter().println("<center><h1>Session expired.</h1></center>");
            } else {
                String password = users.get(username).Password;


                PrintWriter out = response.getWriter();
                String title = "Using POST Method to Read Form Data";
                String docType =
                        "<!doctype html public \"-//w3c//dtd html 4.0 " +
                                "transitional//en\">\n";
                out.println(docType +
                        "<html>\n" +
                        "<head><title>" + title + "</title></head>\n" +
                        "<body bgcolor=\"#f0f0f0\">\n" +
                        "<h1 align=\"center\">" + title + "</h1>\n" +
                        "<ul>\n" +
                        "  <li><b>Username</b>: "
                        + username + "\n" +
                        "  <li><b>Password</b>: "
                        + password + "\n" +
                        "</ul>\n" +
                        "</body></html>");
                logger.log(Level.INFO, "doGet ended...");
            }

        } else {
            response.getWriter().println("<center><h1>Session expired.</h1></center>");
        }

    }
}
