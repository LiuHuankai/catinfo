package com.cat.miao;

import android.view.View;

import android.os.Bundle;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

    }
    public void jumptomain(View view){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void jumptosignup(View view){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }
}
