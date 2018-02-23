package com.example.kevinmendez.twitter_app_mendez;

import android.os.AsyncTask;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

import static com.example.kevinmendez.twitter_app_mendez.MainActivity.PREF_KEY_OAUTH_SECRET;
import static com.example.kevinmendez.twitter_app_mendez.MainActivity.PREF_KEY_OAUTH_TOKEN;
import static com.example.kevinmendez.twitter_app_mendez.MainActivity.TWITTER_CONSUMER_KEY;
import static com.example.kevinmendez.twitter_app_mendez.MainActivity.TWITTER_CONSUMER_SECRET;

/**
 * Created by Kevin Mendez on 2/20/2018.
 */

//public class UpdateStatus extends AsyncTask<String, String, String> {
//    @Override
//    protected String doInBackground(String[] args) {
//        String status = args[0];
//
//        ConfigurationBuilder builder = new ConfigurationBuilder();
//        builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
//        builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
//
//        String access_token = prefs.getString(PREF_KEY_OAUTH_TOKEN, "");
//        // Access Token Secret
//        String access_token_secret = prefs.getString(PREF_KEY_OAUTH_SECRET, "");
//
//        AccessToken accessToken = new AccessToken(access_token, access_token_secret);
//        Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
//       // String message = input.getText().toString();
//        try {
//            twitter.updateStatus(message);
//        } catch (TwitterException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
