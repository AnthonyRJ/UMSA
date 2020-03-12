package com.example.Login_and_Registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class BiometricVerifActivity extends AppCompatActivity {

    private TextView mHeaderLabel;
    private ImageView mFingerprintImage;
    private TextView mParaLabel;

    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric_verif);
        mHeaderLabel = (TextView) findViewById(R.id.headerLab);
        mParaLabel = (TextView) findViewById(R.id.paraLabel);
        mFingerprintImage = (ImageView) findViewById(R.id.fingerprintImage);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if(!fingerprintManager.isHardwareDetected()){
                mParaLabel.setText("Fingerprint Scanner not detected");
            } else if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
                mParaLabel.setText("Permission not granted to use Fingerprint Scanner");
            } else if(!keyguardManager.isKeyguardSecure()){
                mParaLabel.setText("Add Lock to your Phone in Settings");
            } else if(!fingerprintManager.hasEnrolledFingerprints()){
                mParaLabel.setText("You should add atleast 1 fingerprint to use this feature");
            } else {
                mParaLabel.setText("Place your Finger on Scanner to Access the App");
                FingerprintHandler fingerprintHandler = new FingerprintHandler(this);
                fingerprintHandler.startAuth(fingerprintManager, null); //cryptoObject permet de savoir si une nouvelle empreinte à été ajouté depuis la dernière utilisation
            }
        }
    }
}
