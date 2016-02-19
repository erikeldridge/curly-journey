package com.example.dp;

import com.digits.sdk.android.ContactsUploadResult;

public class PrintableContactsUploadResult {
    final ContactsUploadResult result;
    public PrintableContactsUploadResult(ContactsUploadResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        String contents;
        String successCount;
        String totalCount;
        if (result == null) {
            contents = "null";
            successCount = "null";
            totalCount = "null";
        } else {
            contents = String.valueOf(result.describeContents());
            successCount = String.valueOf(result.successCount);
            totalCount = String.valueOf(result.totalCount);
        }
        return "ContactsUploadResult{"
            + ", contents=" + contents
            + ", successCount=" + successCount
            + ", totalCount=" + totalCount
            + "}";
    }
}
