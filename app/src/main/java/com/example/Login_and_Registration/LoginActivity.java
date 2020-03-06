package com.example.Login_and_Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText emailLogin;
    EditText passwordLogin;
    Button connection;
    TextView registration;
    FirebaseAuth fireBaseBD;

    //L'objet qui "detecte" des changements dans la base de donnée
    private FirebaseAuth.AuthStateListener StateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        fireBaseBD = FirebaseAuth.getInstance();

        emailLogin = (EditText) findViewById(R.id.editText_email);
        passwordLogin = (EditText) findViewById(R.id.editText_passwordLogin);
        connection = (Button) findViewById(R.id.button_connection);
        registration = (TextView) findViewById(R.id.textView_registration);

        StateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseU = fireBaseBD.getCurrentUser();
                if (firebaseU != null) {
                    Toast.makeText(LoginActivity.this, "Vous êtes connecté", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, AccueilActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Connectez-vous", Toast.LENGTH_LONG).show();
                }
            }
        };


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
                String email = emailLogin.getText().toString().trim();
                String pwd = passwordLogin.getText().toString().trim();

                if(email.isEmpty()){
                    emailLogin.setError("Entre ton mail ou coronavirus");
                    emailLogin.requestFocus();
                }
                else if(pwd.isEmpty()){
                    passwordLogin.setError("Entre ton mdp ou coronavirus");
                    passwordLogin.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Veuillez renseigner les champs", Toast.LENGTH_LONG).show();
                }
                else {
                    fireBaseBD.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Erreur de connexion, Veuillez réessayer", Toast.LENGTH_LONG).show();
                            } else {
                                startActivity(new Intent(LoginActivity.this, AccueilActivity.class));
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        fireBaseBD.addAuthStateListener(StateListener);
    }
}
