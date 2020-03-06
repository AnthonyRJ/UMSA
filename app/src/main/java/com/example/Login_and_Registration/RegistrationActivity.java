package com.example.Login_and_Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegistrationActivity extends AppCompatActivity {

    EditText emailRegister;
    EditText passwordRegister;
    EditText confirmPassword;
    Button register;
    TextView connection;
    FirebaseAuth fireBaseBD;
    ProgressBar progressBarRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        fireBaseBD = FirebaseAuth.getInstance();
        emailRegister = (EditText) findViewById(R.id.editText_emailRegister);
        passwordRegister = (EditText) findViewById(R.id.editText_passwordRegister);
        confirmPassword = (EditText) findViewById(R.id.editText_confirmPassword);
        register = (Button) findViewById(R.id.button_register);
        connection = (TextView) findViewById(R.id.textView_login);
        progressBarRegister = (ProgressBar) findViewById(R.id.progressBarRegister);

        progressBarRegister.setVisibility(View.INVISIBLE);

        //On ouvre le layout de connexion quand on appuie dessus
        connection.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent moveToLogin = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(moveToLogin);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //trim() enleve les espaces au debut et à la fin
                String email = emailRegister.getText().toString().trim();
                String pwd = passwordRegister.getText().toString().trim();
                String confPwd = confirmPassword.getText().toString().trim();

                progressBarRegister.setVisibility(View.VISIBLE);

                if (email.isEmpty()) {
                    emailRegister.setError("Entre ton mail ou coronavirus");
                    emailRegister.requestFocus();
                } else if (pwd.isEmpty()) {
                    passwordRegister.setError("Entre ton mdp ou coronavirus");
                    passwordRegister.requestFocus();
                } else if (confPwd.isEmpty()) {
                    confirmPassword.setError("Confirme ton mdp ou coronavirus");
                    confirmPassword.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty() && confPwd.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Veuillez renseigner les champs", Toast.LENGTH_LONG).show();
                } else {
                    if(pwd.equals(confPwd)){
                        fireBaseBD.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegistrationActivity.this, "Echec de l'inscription", Toast.LENGTH_LONG).show();
                                } else {
                                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(RegistrationActivity.this, "Votre confirmation de mdp est incorrecte", Toast.LENGTH_LONG).show();
                    }
                }
                progressBarRegister.setVisibility(View.INVISIBLE);
            }
        });
    }
}
