package com.example.dp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Contacts;
import com.digits.sdk.android.ContactsCallback;
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

import io.fabric.sdk.android.DefaultLogger;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SessionListener, AuthCallback {
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String DIGITS_KEY = BuildConfig.DIGITS_KEY;
    private static final String DIGITS_SECRET = BuildConfig.DIGITS_SECRET;
    private static final String TWITTER_KEY = BuildConfig.TWITTER_KEY;
    private static final String TWITTER_SECRET = BuildConfig.TWITTER_SECRET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Constants.TAG, String.format(
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
        Log.d(Constants.TAG, new PrintableDigitsSession(digitsSession).toString());
        TwitterSession twitterSession = Twitter.getSessionManager().getActiveSession();
        if (twitterSession != null) {
            Log.d(Constants.TAG, twitterSession.toString());
        }
        setContentView(R.layout.activity_main);
        Button uploadButton = (Button) findViewById(R.id.upload);
        uploadButton.setOnClickListener(this);
        Button getButton = (Button) findViewById(R.id.get);
        getButton.setOnClickListener(this);
        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.digits_button);
        digitsButton.setCallback(this);
        TwitterLoginButton twitterButton = (TwitterLoginButton) findViewById(R.id.twitter_button);
        twitterButton.setCallback(new TwitterCallback());
        Digits.getInstance().addSessionListener(this);
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
                                    Log.d(Constants.TAG, "result=" + result.toString());
                                } else {
                                    Log.d(Constants.TAG, "result=null");
                                }
                            }

                            @Override
                            public void failure(TwitterException exception) {
                                Log.d(Constants.TAG, exception.toString());
                            }
                        });
                break;
        }
    }

    @Override
    public void changed(DigitsSession session) {
        Log.d(Constants.TAG, "digits session changed, session=" + new PrintableDigitsSession(session).toString());
    }

    @Override
    public void success(DigitsSession session, String phoneNumber) {
        Log.d(Constants.TAG, "digits session success" + ", phonenumber=" + phoneNumber);
        Log.d(Constants.TAG, new PrintableDigitsSession(session).toString());
    }

    @Override
    public void failure(DigitsException exception) {
        Log.d(Constants.TAG, exception.toString());
    }

    class TwitterCallback extends Callback<TwitterSession> {

        @Override
        public void success(Result<TwitterSession> result) {
            Log.d(Constants.TAG, "twitter session success" + ", result=" + result.toString());
        }

        @Override
        public void failure(TwitterException e) {
            Log.d(Constants.TAG, "twitter session failure" + ", e=" + e.getMessage());
        }
    }
}
