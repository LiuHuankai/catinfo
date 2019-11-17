package com.cat.miao.view.Loginfragment;

import android.animation.*;
import android.graphics.Color;
import android.widget.EditText;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.core.view.animation.PathInterpolatorCompat;
import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.cat.miao.R;


import android.content.Context;

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
