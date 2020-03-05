package com.example.Login_and_Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AccueilActivity extends AppCompatActivity {
    Button logout;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener StateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil_activity);

        logout = findViewById(R.id.button_loggout);

        logout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AccueilActivity.this, LoginActivity.class));
            }
        });
    }
}
