package com.example.Login_and_Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.Login_and_Registration.R.id.editText_confirmPassword;

public class RegistrationActivity extends AppCompatActivity {

    EditText usernameRegister;
    EditText passwordRegister;
    EditText confirmPassword;
    Button register;
    TextView login;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        db = new Database(this);
        usernameRegister = (EditText) findViewById(R.id.editText_usernameRegister);
        passwordRegister = (EditText) findViewById(R.id.editText_passwordRegister);
        confirmPassword = (EditText) findViewById(editText_confirmPassword);
        register = (Button) findViewById(R.id.button_register);
        login = (TextView) findViewById(R.id.textView_login);

        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent moveToLogin = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(moveToLogin);
            }
        });

        //Inscription
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = usernameRegister.getText().toString().trim();
                String pwd = passwordRegister.getText().toString().trim();
                String conf_pwd = confirmPassword.getText().toString().trim();

                if(pwd.equals(conf_pwd)){
                    long value = db.addUser(user,pwd);

                    if(value > 0) {
                        Toast.makeText(RegistrationActivity.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(RegistrationActivity.this, LoginActivity.class);
                        startActivity(moveToLogin);
                    }
                    else {
                        Toast.makeText(RegistrationActivity.this, "Votre confirmation de mot de passe est incorrecte", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });
    }
}
