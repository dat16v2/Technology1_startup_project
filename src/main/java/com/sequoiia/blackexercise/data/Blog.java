package com.sequoiia.blackexercise.data;

import com.google.gson.Gson;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;

public class Blog {

    public static BlogPostsJson getBlogPosts(HttpServletRequest request) throws IOException{
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        String filepath = context.getRealPath("WEB-INF/blog_posts.json");
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        String payloadString = "";
        String line;
        while ((line = reader.readLine()) != null) {
            payloadString = payloadString + line;
        }

        BlogPostsJson blogPosts = new Gson().fromJson(payloadString, BlogPostsJson.class);

        /* Print all the retrieved blog posts in console.
        for (int i = 0; i < blogPosts.posts.length; i++)
        {
            System.out.printf("Author: %s\nTitle: %s\nText: %s\n", blogPosts.posts[i].author, blogPosts.posts[i].title ,blogPosts.posts[i].text);
        }
        */

        return blogPosts;
    }

    public static BlogPostRequestJson getBlogPostRequest(HttpServletRequest request) {
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        BufferedReader reader;
        try {
            reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }
        } catch (java.io.IOException ex) {
            System.out.println("Something happened. I should probably handle this shit.");
        }

        BlogPostRequestJson blogPostRequest = new Gson().fromJson(stringBuffer.toString(), BlogPostRequestJson.class);

        return blogPostRequest;
    }

    public static void saveToFile(BlogPostsJson posts, HttpServletRequest request) {
        String payload = new Gson().toJson(posts);
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        String filepath = context.getRealPath("WEB-INF/blog_posts.json");

        try {
            File file = new File(filepath);
            /*
            if (file.isFile()) {
                file.delete();
            }
            */


            FileWriter fw = new FileWriter(file);

            fw.write(payload);

            fw.close();

            System.out.println(payload);

        } catch (IOException ex) {
            System.out.println("Nope, IOException");
        }
    }
}