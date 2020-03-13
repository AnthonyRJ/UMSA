package com.example.Login_and_Registration;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class CustomAccountOnClickListener implements View.OnClickListener {

    private String log;
    private String pass;
    private String appName;
    private Context context;
    public CustomAccountOnClickListener(String log, String pass, String appName, Context context){
        this.log = log;
        this.pass= pass;
        this.appName = appName;
        this.context = context;
    }
    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        Intent intent = new Intent(context, LogInfoActivity.class);
        intent.putExtra("Pseudo", log);
        intent.putExtra("mdp", pass);
        intent.putExtra("AppUID", appName);
        context.startActivity(intent);
    }
}
