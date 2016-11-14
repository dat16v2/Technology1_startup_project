package com.sequoiia.blackexercise.data;

import com.google.gson.*;

public class BlogPost {
    public String title;
    public String author;
    public long timestamp;
    public String text;


    public BlogPost(String title, String author, long timestamp, String text) {
        this.title = title;
        this.author = author;
        this.timestamp = timestamp;
        this.text = text;
    }
    public BlogPost() {

    }
}