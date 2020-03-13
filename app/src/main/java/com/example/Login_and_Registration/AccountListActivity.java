package com.example.Login_and_Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AccountListActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth auth;
    Context context = this;
    LinearLayout ll;
    ImageView img;
    String appName;
    String logTmp;
    String passTmp;
    TextView label;
    Intent i;
    boolean thereIsAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);
        i = getIntent();
        img = (ImageView) findViewById(R.id.appIcon);
        appName = i.getStringExtra("IdApplication");
        db = FirebaseFirestore.getInstance();
        label = findViewById(R.id.AppTitle);
        ll = (LinearLayout) findViewById(R.id.ll);
        auth = FirebaseAuth.getInstance();
        changeIcon(appName.toLowerCase());
        thereIsAccount = false;
        getAccountLinked();
    }

    public void getAccountLinked(){
        db.collection("Comptes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot ds : task.getResult()) {
                        if(ds.exists()) {
                            String currentUID = auth.getCurrentUser().getUid();
                            String UIDTested = (String) ds.get("userID");
                            String AppIDTested = (String) ds.get("Applications");
                            String[] tmpStringArray = AppIDTested.split("_");
                            String AppNameTested = tmpStringArray[0].toLowerCase();
                            if(UIDTested.equals(currentUID) && AppNameTested.equals(appName.toLowerCase())) {
                                if(!thereIsAccount)
                                    thereIsAccount = true;

                                logTmp = (String) ds.get("Pseudo");
                                passTmp = (String) ds.get("mdp");
                                Button b = new Button(context);
                                b.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                b.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                                b.setOnClickListener(new CustomAccountOnClickListener(logTmp, passTmp, appName, AccountListActivity.this));
                                b.setText((String) ds.get("Pseudo"));
                                b.setBackground(getDrawable(R.drawable.button));
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
                                lp.setMargins(30,15,30, 15);
                                b.setLayoutParams(lp);
                                ll.addView(b);
                            }
                        }
                    }
                    if(!thereIsAccount){
                        label.setText("Vous n'avez pas de comptes associés à cette application.");
                    }

                    Button b = new Button(context);
                    b.setText("Ajouter un compte");
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
                    lp.setMargins(30,250,30, 15);
                    b.setLayoutParams(lp);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(AccountListActivity.this, CreateAccountActivity.class);
                            intent.putExtra("IdApplication", i.getStringExtra("IdApplication"));
                        }
                    });
                    b.setBackground(getDrawable(R.drawable.button));
                    ll.addView(b);
                }
            }
        });
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
