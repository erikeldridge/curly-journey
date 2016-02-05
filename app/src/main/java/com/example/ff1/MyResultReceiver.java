package com.example.ff1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.digits.sdk.android.ContactsUploadResult;
import com.digits.sdk.android.ContactsUploadService;

public class MyResultReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ContactsUploadService.UPLOAD_COMPLETE.equals(intent.getAction())) {
            ContactsUploadResult result = intent
                    .getParcelableExtra(ContactsUploadService.UPLOAD_COMPLETE_EXTRA);
            Log.d(Constants.TAG, "result=" + result.toString());
        } else {
            Log.d(Constants.TAG, "result=fail, intent=" + intent.toString());
        }
    }
}
