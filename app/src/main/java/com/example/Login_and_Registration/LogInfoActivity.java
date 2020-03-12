package com.example.Login_and_Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class LogInfoActivity extends AppCompatActivity {

    TextView log;
    TextView pass;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_info);
        Intent intent = getIntent();
        img = (ImageView) findViewById(R.id.appIcon);
        String[] AppUID = intent.getStringExtra("AppUID").split("_");
        changeIcon(AppUID[0]);
        log = (TextView) findViewById(R.id.appLog);
        pass = (TextView) findViewById(R.id.appPassword);

        log.setText(intent.getStringExtra("Pseudo"));
        pass.setText(intent.getStringExtra("mdp"));
    }

    public void changeIcon(String appName){
        if(appName.toLowerCase().equals("netflix")){
            img.setImageResource(R.drawable.netflix_icon);
        }

        if(appName.toLowerCase().equals("facebook")){
            img.setImageResource(R.drawable.facebook_icon);
        }

        if(appName.toLowerCase().equals("twitter")){
            img.setImageResource(R.drawable.twitter_icon);
        }

        if(appName.toLowerCase().equals("twitch")){
            img.setImageResource(R.drawable.twitch_icon);
        }

        if(appName.toLowerCase().equals("snapchat")){
            img.setImageResource(R.drawable.snapchat_icon);
        }

        if(appName.toLowerCase().equals("youtube")){
            img.setImageResource(R.drawable.ytb_icon);
        }

        if(appName.toLowerCase().equals("steam")){
            img.setImageResource(R.drawable.steam_icon);
        }

        if(appName.toLowerCase().equals("epic games") || appName.toLowerCase().equals("fortnite")){
            img.setImageResource(R.drawable.epicgames_icon);
        }

        if(appName.toLowerCase().equals("riot") || appName.toLowerCase().equals("league of legends")){
            img.setImageResource(R.drawable.riot_icon);
        }

        if(appName.toLowerCase().equals("origin")){
            img.setImageResource(R.drawable.origin_icon);
        }

        if(appName.toLowerCase().equals("battle.net")){
            img.setImageResource(R.drawable.bnet_icon);
        }


    }
}
