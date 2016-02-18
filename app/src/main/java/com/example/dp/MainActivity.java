package com.example.dp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Contacts;
import com.digits.sdk.android.ContactsCallback;
import com.digits.sdk.android.ContactsUploadResult;
import com.digits.sdk.android.ContactsUploadService;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.digits.sdk.android.SessionListener;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.DefaultLogger;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SessionListener, AuthCallback {
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    static final String DIGITS_KEY = BuildConfig.DIGITS_KEY;
    static final String DIGITS_SECRET = BuildConfig.DIGITS_SECRET;
    static final String TWITTER_KEY = BuildConfig.TWITTER_KEY;
    static final String TWITTER_SECRET = BuildConfig.TWITTER_SECRET;

    static final String TAG = "dp";
    TwitterLoginButton twitterButton;
    List<String> logBuffer = new ArrayList<>();
    ArrayAdapter<String> logAdapter;
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, logBuffer);
        broadcastReceiver = new ContactsUploadResultReceiver();
        log(String.format(
                "DIGITS_KEY=%s, DIGITS_SECRET=%s, TWITTER_KEY=%s, TWITTER_SECRET=%s",
                DIGITS_KEY, DIGITS_SECRET, TWITTER_KEY, TWITTER_SECRET));
        TwitterAuthConfig twitterConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric fabric = new Fabric.Builder(this)
                .kits(new Twitter(twitterConfig), new Digits())
                .logger(new DefaultLogger(Log.DEBUG))
                .debuggable(true)
                .build();
        Fabric.with(fabric);
        DigitsSession digitsSession = Digits.getSessionManager().getActiveSession();
        log(new PrintableDigitsSession(digitsSession).toString());
        TwitterSession twitterSession = Twitter.getSessionManager().getActiveSession();
        log(new PrintableTwitterSession(twitterSession).toString());
        setContentView(R.layout.activity_main);
        Button uploadButton = (Button) findViewById(R.id.upload);
        uploadButton.setOnClickListener(this);
        Button getButton = (Button) findViewById(R.id.get);
        getButton.setOnClickListener(this);
        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.digits_button);
        digitsButton.setCallback(this);
        twitterButton = (TwitterLoginButton) findViewById(R.id.twitter_button);
        twitterButton.setCallback(new TwitterCallback());
        Digits.getInstance().addSessionListener(this);
        ListView logView = (ListView) findViewById(R.id.log);
        logView.setAdapter(logAdapter);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(broadcastReceiver);
        super.onStop();
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.digits.sdk.android.UPLOAD_COMPLETE");
        filter.addAction("com.digits.sdk.android.UPLOAD_FAILED");
        registerReceiver(broadcastReceiver, filter);
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload:
                Digits.getInstance().getContactsClient().startContactsUpload();
                break;
            default:
                Digits.getInstance().getContactsClient().lookupContactMatches(null, null,
                        new ContactsCallback<Contacts>() {

                            @Override
                            public void success(Result<Contacts> result) {
                                if (result.data.users != null) {
                                    log("result=" + result.toString());
                                } else {
                                    log("result=null");
                                }
                            }

                            @Override
                            public void failure(TwitterException exception) {
                                log(exception.toString());
                            }
                        });
                break;
        }
    }

    @Override
    public void changed(DigitsSession session) {
        log("digits result changed, session=" + new PrintableDigitsSession(session).toString());
    }

    @Override
    public void success(DigitsSession session, String phoneNumber) {
        log("digits result success, session=" + new PrintableDigitsSession(session).toString() + ", phonenumber=" + phoneNumber);
    }

    @Override
    public void failure(DigitsException exception) {
        log(exception.toString());
    }

    class TwitterCallback extends Callback<TwitterSession> {

        @Override
        public void success(Result<TwitterSession> result) {
            log("twitter result success, result=" + new PrintableTwitterResult(result).toString());
        }

        @Override
        public void failure(TwitterException e) {
            log("twitter result failure" + ", e=" + e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        log("twitter result onActivityResult, requestCode=" + requestCode + ", resultCode=" + resultCode + ", data=" + data);

        // Pass the activity result to the login button.
        twitterButton.onActivityResult(requestCode, resultCode, data);
    }

    class ContactsUploadResultReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ContactsUploadService.UPLOAD_COMPLETE.equals(intent.getAction())) {
                ContactsUploadResult result = intent
                        .getParcelableExtra(ContactsUploadService.UPLOAD_COMPLETE_EXTRA);
                log("result=" + result.toString());
            } else {
                log("result=fail, intent=" + intent.toString());
            }
        }
    }

    void log(String text) {
        Log.d(TAG, text);
        logBuffer.add(text);
        logAdapter.notifyDataSetChanged();
    }
}
