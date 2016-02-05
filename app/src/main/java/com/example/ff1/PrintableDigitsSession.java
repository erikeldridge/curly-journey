package com.example.ff1;

import com.digits.sdk.android.DigitsSession;
import com.digits.sdk.android.Email;

public class PrintableDigitsSession {
    final DigitsSession session;
    public PrintableDigitsSession(DigitsSession session) {
        this.session = session;
    }

    @Override
    public String toString() {
        Email email = session.getEmail();
        String address;
        if (email == null) {
            address = "null";
        } else {
            address = email.getAddress();
        }
        return "DigitsSession{"
            + ", isValidUser=" + session.isValidUser()
            + ", getEmail=" + address
            + ", isLoggedOutUser=" + session.isLoggedOutUser()
            + ", getPhoneNumber=" + session.getPhoneNumber()
            + "}";
    }
}
