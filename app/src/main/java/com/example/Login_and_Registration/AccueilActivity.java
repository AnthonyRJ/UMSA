package com.example.Login_and_Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AccueilActivity extends AppCompatActivity {
    Button logout;
    Button test;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil_activity);

        logout = findViewById(R.id.button_loggout);
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

        logout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AccueilActivity.this, LoginActivity.class));
            }
        });
    }
}
