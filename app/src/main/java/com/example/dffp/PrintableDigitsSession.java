package com.example.dffp;

import com.digits.sdk.android.DigitsSession;
import com.digits.sdk.android.Email;

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
        return "PrintableDigitsSession{"
            + ", isValidUser=" + isValidUser
            + ", getEmail=" + address
            + ", isLoggedOutUser=" + isLoggedOutUser
            + ", getPhoneNumber=" + phoneNumber
            + "}";
    }
}
