package com.example.kevin.android_chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilActivity extends AppCompatActivity {

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    private static final int GALLERY_PICK = 1;
    //Storage
    private StorageReference mImageStorage;

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
    private ImageButton mChangeImage;

    //Progress
    private ProgressDialog mProgressDialog;


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
        mChangeImage = (ImageButton) findViewById(R.id.buttonChangeImage);

        //FireBase Storage
        mImageStorage = FirebaseStorage.getInstance().getReference();

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
                Picasso.get().load(image).into(mDisplayImage);

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

        mChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent,"Select Image"),GALLERY_PICK);

                /*CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(ProfilActivity.this);*/
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == GALLERY_PICK && resultCode == RESULT_OK){
            Uri imageUri = data.getData();

            CropImage.activity(imageUri).setAspectRatio(1,1).start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mProgressDialog = new ProgressDialog(ProfilActivity.this);
                mProgressDialog.setTitle("Import en cours ...");
                mProgressDialog.setMessage("Veuillez patientez");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

                Uri resultUri = result.getUri();

                String currentUser = mCurrentUser.getUid();
                StorageReference filepath = mImageStorage.child("profil_images").child(currentUser +".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){

                            String download_url = task.getResult().getDownloadUrl().toString();
                            mUserDatabase.child("image").setValue(download_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        mProgressDialog.dismiss();
                                        Toast.makeText(ProfilActivity.this,"Uploaded",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(ProfilActivity.this,"Erreur upload",Toast.LENGTH_LONG).show();
                            mProgressDialog.dismiss();
                        }
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public static String random(){
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLenght = generator.nextInt(10);
        char tempChar;
        for(int i=0; i<randomLenght; i++){
            tempChar = (char) (generator.nextInt(96)+32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
