package com.example.kevin.android_chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilActivity extends AppCompatActivity {

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    private CircleImageView mDisplayImage;
    //TextView
    private TextView mPseudo;
    private TextView mNom;
    private TextView mEmail;
    private TextView mTelephone;
    //Button
    private ImageButton mEditName;
    private ImageButton mEditPseudo;
    private ImageButton mEditEmail;
    private ImageButton mEditTel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        mDisplayImage = (CircleImageView) findViewById(R.id.profile_image);

        //TextView
        mPseudo = (TextView) findViewById(R.id.textViewPseudo);
        mNom = (TextView) findViewById(R.id.textViewName);
        mEmail = (TextView) findViewById(R.id.textViewEmail);
        mTelephone = (TextView) findViewById(R.id.textViewTelephone);

        //Button
        mEditPseudo = (ImageButton) findViewById(R.id.imageButtonEditPseudo);
        mEditName= (ImageButton) findViewById(R.id.imageButtonEditName);
        mEditEmail = (ImageButton) findViewById(R.id.imageButtonEditEmail);
        mEditTel = (ImageButton) findViewById(R.id.imageButtonEditTelephone);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        String current_uid = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String pseudo = dataSnapshot.child("pseudo").getValue().toString();
                String name = dataSnapshot.child("nom").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String tel = dataSnapshot.child("telephone").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();

                mPseudo.setText(pseudo);
                mNom.setText(name);
                mEmail.setText(email);
                mTelephone.setText(tel);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mEditPseudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pseudo_value = mPseudo.getText().toString();
                Intent pseudoIntent = new Intent(ProfilActivity.this, PseudoActivity.class);
                pseudoIntent.putExtra("pseudo_value",pseudo_value);
                startActivity(pseudoIntent);
            }
        });

    }
}
