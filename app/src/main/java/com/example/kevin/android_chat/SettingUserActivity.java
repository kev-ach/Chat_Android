package com.example.kevin.android_chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingUserActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private TextInputLayout mSetting;
    private Button mButtonSettingSave;

    //Firebase
    private DatabaseReference mSettingDatabase;
    private FirebaseUser mCurrentUser;

    //Progress
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settinguser);

        Intent intent = getIntent();

        mSetting = (TextInputLayout) findViewById(R.id.setting_change);
        mButtonSettingSave = (Button) findViewById(R.id.buttonSettingSave);

        //Firebase
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUser = mCurrentUser.getUid();

        mSettingDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser);

        String pseudo_value = intent.getStringExtra("pseudo_value");
        String name_value = intent.getStringExtra("name_value");
        String email_value = intent.getStringExtra("email_value");
        String telephone_value = intent.getStringExtra("telephone_value");

        String valeur = "";
        String bdd_champs = "";

        if(pseudo_value != null) {
            valeur = pseudo_value;
            bdd_champs = "pseudo";
        }else if(name_value != null) {
            valeur = name_value;
            bdd_champs = "nom";
        }else if(email_value != null) {
            valeur = email_value;
            bdd_champs = "email";
        }else{
            valeur = telephone_value;
            bdd_champs = "telephone";
        }

        mSetting.getEditText().setText(valeur);
        final String finalBdd_champs = bdd_champs;//bdd_champs;
        mButtonSettingSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Progress
                mProgressDialog = new ProgressDialog(SettingUserActivity.this);
                mProgressDialog.setTitle("Sauvegarde en cours");
                mProgressDialog.setMessage("Veuillez patientez");
                mProgressDialog.show();

                String valeur_champs = mSetting.getEditText().getText().toString();
                mSettingDatabase.child(finalBdd_champs).setValue(valeur_champs).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mProgressDialog.dismiss();
                        } else {
                            Toast.makeText(SettingUserActivity.this, "Erreur de sauvegarde", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        mToolbar =(Toolbar) findViewById(R.id.setting_toolbar);
        setSupportActionBar(mToolbar);

        if(pseudo_value != null) {
            getSupportActionBar().setTitle("Profile Pseudo");
        }else if(name_value != null) {
            getSupportActionBar().setTitle("Profile Nom");
        }else if(email_value != null) {
            getSupportActionBar().setTitle("Profile Email");
        }else{
            getSupportActionBar().setTitle("Profile Telephone");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
