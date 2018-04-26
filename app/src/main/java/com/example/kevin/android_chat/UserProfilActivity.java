package com.example.kevin.android_chat;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class UserProfilActivity extends AppCompatActivity {

    private TextView mProfilename,mProfilestatus,mProfiletotalfriends;
    private Button mProfileSendReqBtn,mProfileDeclinereqBtn;
    private ImageView mProfileImage;
    private DatabaseReference mUserDatabase;

    private DatabaseReference mrootUserDatabase;
    private DatabaseReference mRootRef;
    private DatabaseReference mFriendsReqDatabase;
    private DatabaseReference mFriendDatabase;
    private DatabaseReference mNotificationDatabase;
    private FirebaseUser mCurrentUser;
    private ProgressDialog pd;
    private String mCurrentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profil);
    }
}
