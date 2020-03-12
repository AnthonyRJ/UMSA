package com.example.Login_and_Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccueilActivity extends AppCompatActivity {
    Button logout;
    Button createApp;
    Button reload;
    ScrollView apps;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil_activity);

        logout = (Button) findViewById(R.id.button_loggout);
        createApp = (Button) findViewById(R.id.button_createApp);
        reload = (Button) findViewById(R.id.button_reload);
        apps = (ScrollView) findViewById(R.id.scrollView_app);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String id = firebaseAuth.getCurrentUser().getUid();
                Map<String, Object> data = new HashMap<>();
                data.put("iduser", id);

                if(id != null){
                    db.collection("userID").document(id).set(data);
                }
            }
        }, 1000);

        reload.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                afficherApp();
            }
        });

        createApp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent moveToAccountCreation = new Intent(AccueilActivity.this, AppCreationActivity.class);
                startActivity(moveToAccountCreation);
            }
        });

        logout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AccueilActivity.this, LoginActivity.class));
            }
        });
    }

    public void afficherApp(){
        List list = new ArrayList<>();
        db.collection("Applications").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot ds : task.getResult()) {
                        String id = ds.getId();
                        //Test
                        Log.d("TAG", id);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
