package com.example.kevin.android_chat;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
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

public class PseudoActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private TextInputLayout mPseudo;
    private Button mButtonPseudoSave;

    //Firebase
    private DatabaseReference mPseudoDatabase;
    private FirebaseUser mCurrentUser;

    //Progress
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pseudo);

        mPseudo = (TextInputLayout) findViewById(R.id.pseudo_change);
        mButtonPseudoSave = (Button) findViewById(R.id.buttonPseudoSave);

        //Firebase
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUser = mCurrentUser.getUid();

        mPseudoDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser);

        String pseudo_value = getIntent().getStringExtra("pseudo_value");

        mPseudo.getEditText().setText(pseudo_value);
        mButtonPseudoSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Progress
                mProgressDialog = new ProgressDialog(PseudoActivity.this);
                mProgressDialog.setTitle("Sauvegarde en cours");
                mProgressDialog.setMessage("Veuillez patientez");
                mProgressDialog.show();

                String pseudo = mPseudo.getEditText().getText().toString();
                mPseudoDatabase.child("pseudo").setValue(pseudo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            mProgressDialog.dismiss();
                        }else {
                            Toast.makeText(PseudoActivity.this,"Erreur de sauvegarde",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        mToolbar =(Toolbar) findViewById(R.id.pseudo_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Profile Pseudo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
