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
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

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
    private String tmpDocument;
    private ArrayList<String> allDocUID;
    int cptApp;
    TextView moreLab;
    TextView listeLab;


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
        moreLab = (TextView) findViewById(R.id.moreLab);
        moreLab.setVisibility(View.INVISIBLE);
        listeLab = (TextView) findViewById(R.id.listeLab);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        afficherApp();
        cptApp = 0;

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
                                cptApp++;
                                tmpDocument = document.getId();
                                button.setText("" + document.get("nomApplication"));
                                button.setBackground(getDrawable(R.drawable.button));
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                                lp.setMargins(0,10,0,10);
                                button.setLayoutParams(lp);
                                button.setOnClickListener(new View.OnClickListener(){
                                    @Override
                                    public void onClick(View v) {
                                        Button b = (Button) v;
                                        Intent intent = new Intent( AccueilActivity.this , AccountListActivity.class);
                                        intent.putExtra("IdApplication", b.getText());
                                        startActivity(intent);
                                    }
                                });
                                linearLayout.addView(button);
                            }

                            if(cptApp > 4){
                                moreLab.setVisibility(View.VISIBLE);
                            } else if(cptApp <= 0){
                                listeLab.setText("Vous n'avez aucune application enregistrée");
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        return; //On empêche le retour au formulaire de connexion par le bouton back
    }
}
