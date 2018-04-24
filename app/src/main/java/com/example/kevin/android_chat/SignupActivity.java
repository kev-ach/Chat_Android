package com.example.kevin.android_chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mLoginInput = (EditText) findViewById(R.id.signup_main_login);
        mPasswordInput = (EditText) findViewById(R.id.signup_main_mdp);
        mPasswordConfirmInput = (EditText) findViewById(R.id.signup_main_mdpconfirm);
        mSignupButton = (Button) findViewById(R.id.signup_main_submit_btn);

        mValiderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mdp = mPasswordInput.getText().toString();
                String mdp_confirm = mPasswordConfirmInput.getText().toString();

                if (!mdp.equals(mdp_confirm)) {
                    Toast.makeText(MainActivity.this, "Veuillez insérer le même mot de passe.", Toast.LENGTH_LONG).show();
                }
                else  {

                    Intent chatActivityIntent = new Intent(MainActivity.this, ChatActivity.class);
                    startActivity(chatActivityIntent);
                }
            }
        });
    }
}
