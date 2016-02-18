package com.example.dp;

import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterSession;

public class PrintableTwitterResult {
    final Result<TwitterSession> result;
    public PrintableTwitterResult(Result<TwitterSession> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        final String session;
        final String response;
        if (result == null || result.data == null) {
            session = "null";
        } else {
            session = new PrintableTwitterSession(result.data).toString();
        }
        if (result == null || result.response == null) {
            response = "null";
        } else {
            response = result.response.toString();
        }
        return "TwitterResult{"
            + ", session=" + session
            + ", response=" + response
            + "}";
    }
}
