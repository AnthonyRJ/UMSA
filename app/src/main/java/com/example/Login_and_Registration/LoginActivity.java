package com.example.Login_and_Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText usernameLogin;
    EditText passwordLogin;
    Button connection;
    TextView registration;

    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        db = new Database(this);

        usernameLogin = (EditText) findViewById(R.id.editText_usernameLogin);
        passwordLogin = (EditText) findViewById(R.id.editText_passwordLogin);
        connection = (Button) findViewById(R.id.button_connection);
        registration = (TextView) findViewById(R.id.textView_registration);

        //On ouvre le layout d'inscription lorsque l'on clic sur CREER UN COMPTE
        registration.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent moveToRegistration = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(moveToRegistration);
            }
        });

        //On connecte ou pas l'utilisateur
        connection.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //trim() enleve les espaces au debut et à la fin
                String user = usernameLogin.getText().toString().trim();
                String pwd = passwordLogin.getText().toString().trim();
                Boolean res = db.checkUser(user, pwd);

                if(res){
                    Toast.makeText(LoginActivity.this, "Authentification réussie", Toast.LENGTH_SHORT).show();
                    Intent moveToAccueil = new Intent(LoginActivity.this, AccueilActivity.class);
                    startActivity(moveToAccueil);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Erreur : nom utilisateur/mot de passe incorrect", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
