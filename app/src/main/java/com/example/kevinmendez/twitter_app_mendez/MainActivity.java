package com.example.kevinmendez.twitter_app_mendez;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class MainActivity extends AppCompatActivity {
    static String TWITTER_CONSUMER_KEY = "\tnK2vEsi5JzkBElWtOUJA1xLy8"; // place your cosumer key here
    static String TWITTER_CONSUMER_SECRET = "1G2UPYnGa7uCQnBKLL5ReMZ2L2P312209C1oBxW0iYMkzpEHCT\n"; // place your consumer secret here

    // Preference Constants
    static String PREFERENCE_NAME = "twitter_oauth";
    static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";

    static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";

    // Twitter oauth urls
    static final String URL_TWITTER_AUTH = "auth_url";
    static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";

    private static Twitter twitter;
    private static RequestToken requestToken;
    private AccessToken accessToken;


    public SharedPreferences prefs;
    private Button send;
    public EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send = (Button) findViewById(R.id.send);
        input = (EditText) findViewById(R.id.input);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        loginToTwitter();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = input.getText().toString();
                new UpdateStatus().execute(message);

            }
        });
    }

    private void loginToTwitter() {
        // Check if already logged in
        if (!isTwitterLoggedInAlready()) {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
            Configuration configuration = builder.build();
            Toast.makeText(getApplicationContext(),"You are logg in", Toast.LENGTH_LONG).show();
            TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();


            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        requestToken = twitter
                                .getOAuthRequestToken(TWITTER_CALLBACK_URL);
                        MainActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                                .parse(requestToken.getAuthenticationURL())));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        } else {
            // user already logged into twitter
            Toast.makeText(getApplicationContext(),
                    "Already Logged into twitter", Toast.LENGTH_LONG).show();
        }
    }
    private boolean isTwitterLoggedInAlready() {
        // return twitter login status from Shared Preferences
        return prefs.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
    }
    public class UpdateStatus extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String[] args) {
            String status = args[0];

            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);

            String access_token = prefs.getString(PREF_KEY_OAUTH_TOKEN, "");
            // Access Token Secret
            String access_token_secret = prefs.getString(PREF_KEY_OAUTH_SECRET, "");

            AccessToken accessToken = new AccessToken(access_token, access_token_secret);
            Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
            try {

                twitter4j.Status response = twitter.updateStatus(status);
                Toast.makeText(getApplicationContext(),"tweet",Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),response.getText().toString(),Toast.LENGTH_LONG).show();
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            try {
                twitter.updateStatus(status);

            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
