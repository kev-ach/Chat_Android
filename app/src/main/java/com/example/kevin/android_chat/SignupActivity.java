package com.example.kevin.android_chat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private EditText mLoginInput;
    private EditText mPasswordInput;
    private EditText mPasswordConfirmInput;
    private EditText mNom;
    private EditText mPrenom;
    private EditText mEmail;
    private EditText mTelephone;
    private Button mSignupButton;
    private Toolbar mToolbar;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        mLoginInput = (EditText) findViewById(R.id.signup_main_login);
        mPasswordInput = (EditText) findViewById(R.id.signup_main_mdp);
        mPasswordConfirmInput = (EditText) findViewById(R.id.signup_main_mdpconfirm);
        mEmail = (EditText) findViewById(R.id.signup_main_email);
        mNom = (EditText) findViewById(R.id.signup_main_nom);
        mPrenom = (EditText) findViewById(R.id.signup_main_prenom);
        mTelephone = (EditText) findViewById(R.id.signup_main_telephone);
        mSignupButton = (Button) findViewById(R.id.signup_main_submit_btn);

        mToolbar =(Toolbar) findViewById(R.id.register_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Inscription");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mdp = mPasswordInput.getText().toString();
                String mdp_confirm = mPasswordConfirmInput.getText().toString();
                String nom = mNom.getText().toString();
                String prenom = mPrenom.getText().toString();
                String mail = mEmail.getText().toString();
                String telephone = mTelephone.getText().toString();

                if (!mdp.equals(mdp_confirm)) {
                    Toast.makeText(SignupActivity.this, "Veuillez insérer le même mot de passe.", Toast.LENGTH_LONG).show();
                }
                else  {
                    register_user(nom,prenom,mail,mdp,telephone);
                }
            }
        });
    }

    private void register_user(String nom, String prenom, String mail, String mdp, String telephone) {

        mAuth.createUserWithEmailAndPassword(mail, mdp)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent mainIntent = new Intent(SignupActivity.this, ChatActivity.class);
                            startActivity(mainIntent);
                            finish();
                        } else {
                            Toast.makeText(SignupActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
