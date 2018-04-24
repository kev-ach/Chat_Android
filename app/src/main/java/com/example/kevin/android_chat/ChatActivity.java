package com.example.kevin.android_chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChatActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button modifierProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAuth = FirebaseAuth.getInstance();

        modifierProfil = (Button) findViewById((R.id.activity_main_valider_btn));

        modifierProfil.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profilActivityIntent = new Intent(ChatActivity.this, ProfilActivity.class);
                startActivity(profilActivityIntent);
            }
        }));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){

            Intent startIntent = new Intent(ChatActivity.this, MainActivity.class);
            startActivity(startIntent);
            finish();
        }
    }
}
