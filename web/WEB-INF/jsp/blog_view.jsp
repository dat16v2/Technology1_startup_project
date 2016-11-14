<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sequoiia.blackexercise.data.*, com.sequoiia.blackexercise.models.User, com.sequoiia.blackexercise.models.UserRole, javax.servlet.ServletException, javax.servlet.http.HttpServlet, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.io.IOException, java.io.PrintWriter, java.util.HashMap" %>
<%@ page import="com.sequoiia.blackexercise.data.Cookie" %>
<html>
<head>
    <title>My adventure to North Korea</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset=“utf-8”>
</head>
<body style="margin: 0px;">
<%
    Cookie cookies = com.sequoiia.blackexercise.data.Cookie.getCookies(request.getCookies());
    HashMap<String, User> users = com.sequoiia.blackexercise.models.User.getUsers("users.txt", request);
    UserRole role;
    BlogPostsJson blogPosts = Blog.getBlogPosts(request);

    if (cookies.username.equals("")) {
        role = UserRole.PUBLIC;
    } else {
        role = users.get(cookies.username).Role;
    }
%>
<div style="width: 90%; margin: 0 auto;">
    <div id="header" style="background-color: aquamarine;">
        <div id="title" style="display: flex; justify-content: center; align-items: center; "> <h2>Blog title</h2></div>
        <div id="loginbar>"><span style="font-size: 0.7rem; display: inline-block;"><% out.print(String.format("Welcome %s, access level: %d", cookies.username, role.getUserCode())); %> </span> </div>
    </div>
<center>
    <p>
        <%
            for (int i = 0; i < blogPosts.posts.size(); i++) {
                BlogPost current = blogPosts.posts.get(i);
                out.print(String.format("<p><h2>%s</h2><br>Author: %s<br>%s</p><br>", current.title, current.author, current.text));
            }
        %>
    </p>

        <%
            if (role == UserRole.ADMIN) {
        %>
        <div>
            <input id="blogTitle" placeholder="Title" style="display: block;">
            <textarea id="blogContent" placeholder="content" style="display: block;"></textarea>
            <button type="button" id="submitBlogPost" value="Submit" style="display: block;">Submit</button>
            <div id="statusMessage"></div>
        </div>
        <%
            }
        %>
</center>
</div>

<%
    if (role == UserRole.ADMIN) {
%>
<script>
    var submitButton = document.getElementById("submitBlogPost");
    var blogTitle = document.getElementById("blogTitle");
    var blogContent = document.getElementById("blogContent");
    var statusMessage = document.getElementById("statusMessage");
    if (submitButton) {
        submitButton.addEventListener("click", function(event) {
            console.log(blogTitle.value);
            console.log(blogContent.value);

            var data = {"title": blogTitle.value, "text": blogContent.value};

            var req = new XMLHttpRequest();
            req.addEventListener("load", function() {
                var response = JSON.parse(this.responseText);
                if (response.status == 1) {
                    statusMessage.innerHTML = response.message + " Updating..";

                    setTimeout(function() {
                        window.location = "/blog";
                    }, 3000);

                } else {
                    statusMessage.innerHTML = response.message;
                }
            });
            req.open("POST", "/api/create");
            req.send(JSON.stringify(data));
        });
    }
</script>
<%
    }
%>
</body>
</html>
