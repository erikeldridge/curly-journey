package com.example.dp;

import com.twitter.sdk.android.core.TwitterSession;

public class PrintableTwitterSession {
    final TwitterSession session;

    PrintableTwitterSession(TwitterSession session) {
        this.session = session;
    }

    @Override
    public String toString() {
        final String token;
        final String secret;
        final String userName;
        final String userId;
        if (session == null || session.getAuthToken() == null) {
            token = "null";
            secret = "null";
            userName = "null";
            userId = "null";
        } else {
            token = session.getAuthToken().token;
            secret = session.getAuthToken().secret;
            userName = session.getUserName();
            userId = String.valueOf(session.getUserId());
        }
        return "TwitterSession{"
                + ", token=" + token
                + ", secret=" + secret
                + ", userName=" + userName
                + ", userId=" + userId
                + "}";
    }
}
