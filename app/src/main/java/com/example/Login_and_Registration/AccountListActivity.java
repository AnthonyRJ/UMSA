package com.example.Login_and_Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

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
    ScrollView sv;
    RelativeLayout r1;
    String appName;
    String logTmp;
    String passTmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);


       // Intent i = getIntent();
       // String appName = i.getExtras().getString("IdApplication");
        appName = "facebook";
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        r1 = (RelativeLayout)findViewById(R.id.r1);
        sv = new ScrollView(this);
        ll = new LinearLayout(this);
        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);
        getAccountLinked();
        r1.addView(sv);
    }

    public void getAccountLinked(){
        List list = new ArrayList<>();
        db.collection("Comptes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot ds : task.getResult()) {
                        if(ds.exists()) {
                            String currentUID = auth.getCurrentUser().getUid();
                            String UIDTested = (String) ds.get("userID");
                            String AppIDTested = (String) ds.get("Applications");
                            if(UIDTested.equals(currentUID) && AppIDTested.contains(appName)) {
                                logTmp = (String) ds.get("Pseudo");
                                passTmp = (String) ds.get("mdp");
                                Button b = new Button(context);
                                b.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(AccountListActivity.this, LogInfoActivity.class);
                                        intent.putExtra("Pseudo", logTmp);
                                        intent.putExtra("mdp", passTmp);
                                        startActivity(intent);
                                    }
                                });
                                b.setText((String) ds.get("Pseudo"));
                                ll.addView(b);
                            }
                        }
                    }
                }
            }
        });
    }
}
