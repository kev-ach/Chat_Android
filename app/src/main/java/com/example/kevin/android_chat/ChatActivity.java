package com.example.kevin.android_chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChatActivity extends AppCompatActivity {

    private Button modifierProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        modifierProfil = (Button) findViewById((R.id.activity_main_valider_btn));

        modifierProfil.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profilActivityIntent = new Intent(ChatActivity.this, ProfilActivity.class);
                startActivity(profilActivityIntent);
            }
        }));
    }
}
