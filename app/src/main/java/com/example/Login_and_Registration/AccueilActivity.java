package com.example.Login_and_Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccueilActivity extends AppCompatActivity {
    Button logout;
    Button createApp;
    Button reload;
    ScrollView apps;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    final Handler handler = new Handler();
    private LinearLayout linearLayout;
    private QueryDocumentSnapshot tmpDocument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil_activity);

        logout = (Button) findViewById(R.id.button_loggout);
        createApp = (Button) findViewById(R.id.button_createApp);
        reload = (Button) findViewById(R.id.button_reload);
        apps = (ScrollView) findViewById(R.id.scrollView_app);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        ((LinearLayout) linearLayout).removeAllViews();
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        afficherApp();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String id = firebaseAuth.getCurrentUser().getUid();
                Map<String, Object> data = new HashMap<>();
                data.put("iduser", id);

                if(id != null){
                    db.collection("userID").document(id).set(data);
                }
            }
        }, 1000);

        reload.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                afficherApp();
            }
        });

        createApp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent moveToAccountCreation = new Intent(AccueilActivity.this, AppCreationActivity.class);
                startActivity(moveToAccountCreation);
            }
        });

        logout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AccueilActivity.this, LoginActivity.class));
            }
        });
    }

    public void afficherApp(){



        ScrollView sv = (ScrollView) findViewById(R.id.scrollView_app);
        final Context context = this;

        ((LinearLayout) linearLayout).removeAllViews();

        db.collection("Applications")
                .whereEqualTo("userID", firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    private static final String TAG = "TAG" ;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Button button = new Button(context);
                                tmpDocument = document;
                                button.setText("" + document.get("nomApplication"));
                                button.setOnClickListener(new View.OnClickListener(){
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent( AccueilActivity.this , AccountListActivity.class);
                                        intent.putExtra("IdApplication", tmpDocument.getId());
                                        startActivity(intent);
                                    }
                                });
                                linearLayout.addView(button);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AccueilActivity.this, AccountListActivity.class);
        startActivity(intent);
    }
}
