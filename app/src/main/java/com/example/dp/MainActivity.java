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
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SessionListener, AuthCallback {
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = BuildConfig.TWITTER_KEY;
    private static final String TWITTER_SECRET = BuildConfig.TWITTER_SECRET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Constants.TAG, String.format("TWITTER_KEY=%s, TWITTER_SECRET=%s", TWITTER_KEY, TWITTER_SECRET));
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits());
        DigitsSession session = Digits.getSessionManager().getActiveSession();
        Log.d(Constants.TAG, new PrintableDigitsSession(session).toString());
        setContentView(R.layout.activity_main);
        Button uploadButton = (Button) findViewById(R.id.upload);
        uploadButton.setOnClickListener(this);
        Button getButton = (Button) findViewById(R.id.get);
        getButton.setOnClickListener(this);
        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        digitsButton.setCallback(this);
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
        Log.d(Constants.TAG, new PrintableDigitsSession(session).toString());
    }

    @Override
    public void success(DigitsSession session, String phoneNumber) {
        Log.d(Constants.TAG, "session success" + ", phonenumber=" + phoneNumber);
        Log.d(Constants.TAG, new PrintableDigitsSession(session).toString());
    }

    @Override
    public void failure(DigitsException exception) {
        Log.d(Constants.TAG, exception.toString());
    }
}
