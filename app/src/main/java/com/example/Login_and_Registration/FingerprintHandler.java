package com.example.Login_and_Registration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback{
    private Context context;

    public FingerprintHandler(Context context){
        this.context = context;
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject,cancellationSignal ,0,this, null);

    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString){
        this.update("Il y a eu une erreur, " + errString, false);
    }

    @Override
    public void onAuthenticationFailed(){
        this.update("L'identification à échoué.", false);
    }

    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result){
        this.update("Bienvenue sur UMSA.", true);
        Intent moveToLogin = new Intent(context , LoginActivity.class);
        context.startActivity(moveToLogin);
    }


    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString){
        this.update("Erreur : " + helpString, false);
    }
    private void update(String s, boolean b) {
        TextView paraLabel = (TextView) ((Activity)context).findViewById(R.id.paraLabel);
        ImageView imageView = (ImageView) ((Activity)context).findViewById(R.id.fingerprintImage);
        paraLabel.setText(s);

        if(b == false) {
            paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        } else{
            paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            imageView.setImageResource(R.mipmap.done);
        }
    }
}

