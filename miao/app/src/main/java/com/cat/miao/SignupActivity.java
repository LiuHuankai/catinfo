package com.cat.miao;

import android.util.Log;
import android.view.View;

import android.os.Bundle;
import android.content.Intent;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.cat.miao.model.SignBean;
import com.cat.miao.network.RxRetrofitForSign;

public class SignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        LinearLayout signUpButton = (LinearLayout) findViewById(R.id.sign_up_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxRetrofitForSign.getInstens().getSignInfo(new RxRetrofitForSign.CallBack() {
                    @Override
                    public void onSuccess(SignBean signBean) {
                        Log.e("error", "你活了" );
                        Log.e("error", signBean.getMessage());
                        Log.e("error", signBean.getCode() );
                    }

                    @Override
                    public void onError() {
                        Log.e("error", "你死了" );
                    }
                });
            }
        });

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
