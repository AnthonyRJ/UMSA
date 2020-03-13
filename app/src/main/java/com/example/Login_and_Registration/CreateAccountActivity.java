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

public class CreateAccountActivity extends AppCompatActivity {

    Button addAccount;
    EditText accountName;
    EditText accountPassword;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        addAccount = (Button) findViewById(R.id.button_createAccount2);
        accountName = (EditText) findViewById(R.id.editText_accountName);
        accountPassword = (EditText) findViewById(R.id.editText_passwordAccount);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        addAccount.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                String id = auth.getCurrentUser().getUid();
                String name = accountName.getText().toString().trim();
                String mdp = accountPassword.getText().toString().trim();
                String IdApp = intent.getStringExtra("IdApplication");

                Map<String, Object> data = new HashMap<>();
                data.put("Applications", IdApp);
                data.put("Pseudo", name);
                try {
                    data.put("mdp", EncryptDecrypt.encrypt(mdp));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                data.put("userID", id);

                db.collection("Comptes").add(data);

                Intent moveToAccountList = new Intent(CreateAccountActivity.this, AccountListActivity.class);
                moveToAccountList.putExtra("IdApplication", IdApp);
                startActivity(moveToAccountList);
            }
        });



    }
}
