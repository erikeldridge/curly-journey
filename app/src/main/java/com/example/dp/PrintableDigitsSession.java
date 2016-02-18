package com.example.dp;

import com.digits.sdk.android.DigitsSession;

public class PrintableDigitsSession {
    final DigitsSession session;
    public PrintableDigitsSession(DigitsSession session) {
        this.session = session;
    }

    @Override
    public String toString() {
        String address;
        String phoneNumber;
        String isValidUser;
        String isLoggedOutUser;
        if (session == null) {
            address = "null";
            phoneNumber = "null";
            isValidUser = "null";
            isLoggedOutUser = "null";
        } else {
            isValidUser = String.valueOf(session.isValidUser());
            isLoggedOutUser = String.valueOf(session.isLoggedOutUser());
            phoneNumber = session.getPhoneNumber();
            if (session.getEmail() == null) {
                address = "null";
            } else {
                address = session.getEmail().getAddress();
            }
        }
        return "DigitsSession{"
            + ", isValidUser=" + isValidUser
            + ", getEmail=" + address
            + ", isLoggedOutUser=" + isLoggedOutUser
            + ", getPhoneNumber=" + phoneNumber
            + "}";
    }
}
