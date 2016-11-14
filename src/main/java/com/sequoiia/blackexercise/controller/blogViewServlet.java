package com.sequoiia.blackexercise.controller;

import com.sequoiia.blackexercise.data.*;
import com.sequoiia.blackexercise.models.User;
import com.sequoiia.blackexercise.models.UserRole;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class blogViewServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie cookies = Cookie.getCookies(request.getCookies());
        HashMap<String, User> users = com.sequoiia.blackexercise.models.User.getUsers("WEB-INF/users.txt", request);
        //HashMap<String, User> users = com.sequoiia.blackexercise.models.User.getUsersGCS("users.txt");

        UserRole Role;

        if (cookies.username.equals("")) {
            Role = UserRole.PUBLIC;
        } else {
            Role = users.get(cookies.username).Role;
        }

        System.out.printf("User %s is logged in with the permission %d", cookies.username, Role.getUserCode());

        PrintWriter out = response.getWriter();
        response.setStatus(200);

        out.println("<head><title>Blog view</title></head><body><center><h1>Blog view</h1></center></body>");
        String currentUser = String.format("User %s is logged in with the permission %d<br>", cookies.username, Role.getUserCode());
        out.println(currentUser);

        BlogPostsJson blogPosts = Blog.getBlogPosts(request);
        for (int i = 0; i < blogPosts.posts.size(); i++) {
            BlogPost current = blogPosts.posts.get(i);
            out.printf("<p><h2>%s</h2><br>Author: %s<br>%s</p><br>", current.title, current.author, current.text);
        }
    }
}
