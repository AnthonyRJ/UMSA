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

    EditText usernameRegister;
    EditText passwordRegister;
    EditText confirmPassword;
    EditText phoneRegister;
    EditText emailRegister;
    Button registerBtn;
    TextView loginBtn;
    ProgressBar progressBarRegister;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        /* initialisation des données dans l'activité en recherchant les input utilisateurs par id */
        usernameRegister = (EditText) findViewById(R.id.editText_usernameRegister);
        passwordRegister = (EditText) findViewById(R.id.editText_passwordRegister);
        confirmPassword = (EditText) findViewById(R.id.editText_confirmPassword);
        phoneRegister = (EditText) findViewById(R.id.editText_phoneRegister);
        emailRegister = (EditText) findViewById(R.id.editText_emailRegister);

        registerBtn = (Button) findViewById(R.id.button_register);
        loginBtn = (TextView) findViewById(R.id.textView_login);
        progressBarRegister = (ProgressBar) findViewById(R.id.progressBarRegister);

        //Firebase object
        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), AccueilActivity.class));
        }

        registerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Intent moveToLogin = new Intent(RegistrationActivity.this, LoginActivity.class);
                //startActivity(moveToLogin);
                String username = usernameRegister.getText().toString().trim();
                String email = emailRegister.getText().toString().trim();
                String password = passwordRegister.getText().toString().trim();
                String confPass = confirmPassword.getText().toString().trim();
                String phone = phoneRegister.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    emailRegister.setError("L'adresse mail est nécessaire pour l'inscription");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    passwordRegister.setError("Un mot de passe est nécessaire pour l'inscription");
                    return;
                }

                if (password.length() > 16) {
                    passwordRegister.setError("Le mot de passe indiqué est trop long");
                    return;
                }

                if (password.length() < 6) {
                    passwordRegister.setError("Le mot de passe indiqué est trop court");
                    return;
                }

                if (TextUtils.isEmpty(username)) {
                    usernameRegister.setError("Un nom d'utilisateur est nécessaire pour l'inscription");
                    return;
                }

                if (!confPass.contentEquals(password)) {
                    confirmPassword.setError("La confirmation ne correspond pas au mot de passe indiqué");
                    return;
                }

                progressBarRegister.setVisibility(View.VISIBLE);

                //inscription de l'utilisateur

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegistrationActivity.this, "Utilisateur inscrit", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), AccueilActivity.class));
                        }else{
                            Toast.makeText(RegistrationActivity.this, "Erreur : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
    }
}
