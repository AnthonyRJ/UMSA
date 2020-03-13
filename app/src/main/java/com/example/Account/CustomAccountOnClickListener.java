package com.example.Account;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class CustomAccountOnClickListener implements View.OnClickListener {

    private String log;
    private String pass;
    private String appName;
    private String docRef;
    private Context context;
    public CustomAccountOnClickListener(String log, String pass, String appName, String docRef, Context context){
        this.log = log;
        this.pass= pass;
        this.appName = appName;
        this.context = context;
        this.docRef = docRef;
    }
    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        Intent intent = new Intent(context, LogInfoActivity.class);
        intent.putExtra("Pseudo", log);
        intent.putExtra("mdp", pass);
        intent.putExtra("AppUID", appName);
        intent.putExtra("DocRef", docRef);
        context.startActivity(intent);
    }
}
