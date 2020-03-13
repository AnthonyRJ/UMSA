package com.example.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Login_and_Registration.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogInfoActivity extends AppCompatActivity {

    TextView log;
    TextView pass;
    ImageView img;
    FirebaseFirestore db;
    DocumentSnapshot ds;
    String IdApplication;
    String DocumentRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_log_info);
        Intent intent = getIntent();
        img = (ImageView) findViewById(R.id.appIcon);
        IdApplication = intent.getStringExtra("AppUID");
        DocumentRef = intent.getStringExtra("DocRef");
        String[] AppUID = IdApplication.split("_");
        changeIcon(AppUID[0]);
        log = (TextView) findViewById(R.id.appLog);
        pass = (TextView) findViewById(R.id.appPassword);
        Button delButton = (Button) findViewById(R.id.delButton);
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Comptes").document(DocumentRef).delete();
                Intent moveToAccountList = new Intent(LogInfoActivity.this, AccountListActivity.class);
                moveToAccountList.putExtra("IdApplication", IdApplication);
                startActivity(moveToAccountList);
            }
        });

        log.setText(intent.getStringExtra("Pseudo"));
        try {
            pass.setText(EncryptDecrypt.decrypt(intent.getStringExtra("mdp")));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
