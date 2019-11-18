package com.cat.miao;

import android.view.View;

import android.os.Bundle;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

    }
    public void jumptologin(View view){
        Intent intent = new Intent();
        intent.setClass(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    public void jumptomain(View view){
        Intent intent = new Intent();
        intent.setClass(SignupActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
