package com.sequoiia.blackexercise.data;

public class Cookie {
    public String username = "";
    public Long timestamp = (long) 0;
    public String status = "";

    public static Cookie getCookies(javax.servlet.http.Cookie cookies[]) {
        String username = "";
        Long timestamp = (long) 0;
        String status = "";
        Cookie payload = new Cookie();

        if (cookies != null)
        {
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

            }

            payload.username = username;
            payload.timestamp = timestamp;
            payload.status = status;
        }
        return payload;
    }
}