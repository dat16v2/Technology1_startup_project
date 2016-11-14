package com.sequoiia.blackexercise.models;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.HashMap;

import com.google.appengine.tools.cloudstorage.*;
import com.google.cloud.sql.jdbc.internal.Url;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class User {
    public String Username;
    public String Password;
    public UserRole Role;

    User(String username, String password, int role) {
        Username = username;
        Password = password;
        switch(role)
        {
            case 1:
                Role = UserRole.PUBLIC;
                break;
            case 2:
                Role = UserRole.ADMIN;
                break;
            case 3:
                Role = UserRole.USER;
                break;
            default:
                Role = UserRole.PUBLIC;
                break;
        }
    }

    User() {

    }

    static User parseLine(String text) {
        String username = "";
        String password = "";
        int role = 1;
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
                    turn = turn + 1;
                }
                continue;
            }

            if (text.charAt(i) == ',')
            {
                continue;
            }


            switch (turn)
            {
                case 0:
                    username = username + text.charAt(i);
                    break;
                case 1:
                    password = password + text.charAt(i);
                    break;
                case 2:
                    role = Character.getNumericValue(text.charAt(i));
                    break;
                default:
                    System.out.println("Not supposed to be here, ooops.");
                    break;
            }

        }
        System.out.println(role);
        return new User(username, password, role);
    }

    public static HashMap<String, User> getUsers(String path, HttpServletRequest request) throws IOException {
        HashMap<String, User> users = new HashMap<String, User>();

        //BufferedReader reader = new BufferedReader(new FileReader("WEB-INF/users.txt"));
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        String filepath = context.getRealPath("WEB-INF/users.txt");
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        String line;
        while ((line = reader.readLine()) != null) {
            User user = parseLine(line);
            users.put(user.Username.toLowerCase(), user);
        }

        reader.close();

        return users;
    }
    /*
    private static final GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
            .initialRetryDelayMillis(10)
            .retryMaxAttempts(10)
            .totalRetryPeriodMillis(15000)
            .build());

    public static void tmpShit() throws IOException
    {
        GcsFileOptions options = new GcsFileOptions.Builder()
                .mimeType("text/txt")
                .acl("public-read")
                .addUserMetadata("myfield1", "my field value")
                .build();
        GcsFilename fileName = new GcsFilename("kea-dat16v2.appspot.com", "woo.txt");

        GcsOutputChannel writeChannel = gcsService.createOrReplace(fileName, options);
        PrintWriter writer = new PrintWriter(Channels.newWriter(writeChannel, "UTF8"));
        writer.println("The woods are lovely dark and deep.");
        writer.println("But I have promises to keep.");
        writer.flush();

        writeChannel.waitForOutstandingWrites();

        writeChannel.write(ByteBuffer.wrap("And miles to go before I sleep.".getBytes("UTF8")));

        writeChannel.close();
    }

    public static HashMap<String, User> getUsersGCS(String path) throws IOException{
        BufferedReader reader = null;
        HashMap<String, User> users = new HashMap<String, User>();
        GcsFilename fileName = new GcsFilename("kea-dat16v2.appspot.com", path);
        GcsInputChannel readChannel = null;

        tmpShit();

        try {
            readChannel = gcsService.openReadChannel(fileName, 0);
            reader = new BufferedReader(Channels.newReader(readChannel, "UTF8"));
            String line;
            while ((line = reader.readLine()) != null) {
                User user = parseLine(line);
                users.put(user.Username.toLowerCase(), user);
            }
        } finally {
            if (reader != null)
            {
                reader.close();
            }
        }

        return users;
    }
    */
}
