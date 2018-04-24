package com.example.kevin.android_chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mLoginInput;
    private EditText mPasswordInput;
    private Button mValiderButton;
    private Button mSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoginInput = (EditText) findViewById(R.id.activity_main_login);
        mPasswordInput = (EditText) findViewById(R.id.activity_main_mdp);
        mValiderButton = (Button) findViewById((R.id.activity_main_valider_btn));
        mSignupButton = (Button) findViewById((R.id.activity_main_signup));

        mValiderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mdp = mPasswordInput.getText().toString();
                String login = mLoginInput.getText().toString();

                if (login.equals("") && mdp.equals("")) {
                    Toast.makeText(MainActivity.this, "Identifiant et/ou Mot de passe obligatoire", Toast.LENGTH_SHORT).show();
                } else if (login.equals("") && mdp.equals("test")) {
                    // Si mdp_local(le mot de passe entrer) = au mot de passe enregistrer dans SharedPreference alors redirige vers AddProjectActivity
                        /*for (int i = 0; i < 2; i++) {
                            Toast.makeText(MainActivity.this, "Mot de passe : " + mdp, Toast.LENGTH_SHORT).show();
                        }*/
                    Intent chatActivityIntent = new Intent(MainActivity.this, ChatActivity.class);
                    startActivity(chatActivityIntent);
                } else {
                    for (int i = 0; i < 2; i++) {
                        Toast.makeText(MainActivity.this, "Mot de passe incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mSignupButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupActivityIntent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(signupActivityIntent);
            }
        }));
    }
}
