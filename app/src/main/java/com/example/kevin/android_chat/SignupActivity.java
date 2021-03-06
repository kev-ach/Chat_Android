package com.example.kevin.android_chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

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
    private ProgressDialog mRegProgressDialog;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        mRegProgressDialog = new ProgressDialog(this);

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

                String pseudo = mLoginInput.getText().toString();
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
                    if(!TextUtils.isEmpty(pseudo) &&
                            !TextUtils.isEmpty(nom) &&
                            !TextUtils.isEmpty(prenom) &&
                            !TextUtils.isEmpty(mail) &&
                            !TextUtils.isEmpty(telephone) &&
                            !TextUtils.isEmpty(mdp) &&
                            !TextUtils.isEmpty(mdp_confirm)){

                        mRegProgressDialog.setTitle("Inscription en cours");
                        mRegProgressDialog.setMessage("Veuillez patientez");
                        mRegProgressDialog.setCanceledOnTouchOutside(false);
                        mRegProgressDialog.show();
                        register_user(pseudo,nom,prenom,mail,mdp,telephone);
                    }else {
                        Toast.makeText(SignupActivity.this,"Veuillez remplir les champs",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    private void register_user(final String pseudo, final String nom, final String prenom, final String mail, String mdp, final String telephone) {

        mAuth.createUserWithEmailAndPassword(mail, mdp)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = currentUser.getUid();

                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                            HashMap<String,String> userMap =  new HashMap<>();
                            userMap.put("pseudo",pseudo);
                            userMap.put("nom",nom);
                            userMap.put("prenom",prenom);
                            userMap.put("email",mail);
                            userMap.put("telephone",telephone);
                            userMap.put("image","default");
                            userMap.put("thumb_image","default");

                            mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        mRegProgressDialog.dismiss();
                                        Intent mainIntent = new Intent(SignupActivity.this, ChatActivity.class);
                                        startActivity(mainIntent);
                                        finish();
                                    }
                                }
                            });
                        } else {

                            mRegProgressDialog.hide();
                            Toast.makeText(SignupActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
