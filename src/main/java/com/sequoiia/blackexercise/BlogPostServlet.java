package com.sequoiia.blackexercise;

import com.sequoiia.LoginServlet;
import com.sequoiia.blackexercise.data.*;
import com.sequoiia.blackexercise.models.User;
import com.sequoiia.blackexercise.models.UserRole;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;

public class BlogPostServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(LoginServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie cookies = com.sequoiia.blackexercise.data.Cookie.getCookies(request.getCookies());
        HashMap<String, User> users = com.sequoiia.blackexercise.models.User.getUsers("users.txt", request);
        response.setHeader("Content-Type", "application/json");
        String errorPayloadUnauthenticated = "{\"status\": 0, \"message\": \"User unauthenticated.\"}";

        if (users.containsKey(cookies.username.toLowerCase())) {
            if (users.get(cookies.username.toLowerCase()).Role == UserRole.ADMIN) {
                BlogPostsJson blogPosts = Blog.getBlogPosts(request);
                BlogPostRequestJson blogPostRequest = Blog.getBlogPostRequest(request);

                BlogPost post = new BlogPost();
                post.author = cookies.username;
                Date currentTime = new Date();
                post.timestamp = currentTime.getTime();
                post.title = blogPostRequest.title;
                post.text = blogPostRequest.text;

                blogPosts.posts.add(post);

                Blog.saveToFile(blogPosts, request);
                response.getWriter().println("{\"status\": 1, \"message\": \"Success.\"}");
            }
        } else {
            response.getWriter().println(errorPayloadUnauthenticated);
        }
    }
}
