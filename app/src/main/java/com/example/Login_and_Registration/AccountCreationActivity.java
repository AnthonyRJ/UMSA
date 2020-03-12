package com.example.Login_and_Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AccountCreationActivity extends AppCompatActivity {

    Button addApp;
    EditText appName;

    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

        addApp = (Button) findViewById(R.id.button_createAccount);
        appName = (EditText) findViewById(R.id.editText_appName);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        addApp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                String nomApp = appName.getText().toString().trim();

                Map<String, Object> data = new HashMap<>();
                data.put("nomApplication", nomApp);

                String path = nomApp + "_" + auth.getCurrentUser().getUid();
                db.collection("Applications").document(path).set(data);

                startActivity(new Intent(AccountCreationActivity.this, AccueilActivity.class));
            }
        });
    }


}
