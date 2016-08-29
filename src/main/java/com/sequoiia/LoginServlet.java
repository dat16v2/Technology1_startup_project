package com.sequoiia;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
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

    static UserPass parseLine(String text) {
        String username = "";
        String password = "";
        boolean active = false;
        int turn = 0;


        for (int i = 0; i < text.length(); i++)
        {
            if (text.charAt(i) == '"')
            {
                if (!active)
                {
                    active = true;
                } else {
                    active = false;
                    turn = 1;
                }
                continue;
            }

            if (text.charAt(i) == ',')
            {
                continue;
            }

            if (turn == 0)
            {
                username = username + text.charAt(i);
            } else {
                password = password + text.charAt(i);
            }
        }

        return new UserPass(username, password);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        response.setHeader("Content-Type", "application/json");

        HashMap<String, String> users = new HashMap<String, String>();

        BufferedReader reader = new BufferedReader(new FileReader("WEB-INF/users.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            UserPass user = parseLine(line);
            users.put(user.Username.toLowerCase(), user.Password);
        }

        reader.close();


        if (users.containsKey(username.toLowerCase())) {
            if (users.get(username.toLowerCase()).equals(password)) {
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