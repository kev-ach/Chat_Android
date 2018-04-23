package com.example.kevin.android_chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText mLoginInput;
    private EditText mPasswordInput;
    private Button mValiderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoginInput = (EditText) findViewById(R.id.activity_main_login);
        mPasswordInput = (EditText) findViewById(R.id.activity_main_mdp);
        mValiderButton = (Button) findViewById((R.id.activity_main_valider_btn));
    }
}
