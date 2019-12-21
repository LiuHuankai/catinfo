package com.cat.miao.view;

import android.util.Log;
import android.view.View;

import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.cat.miao.MainActivity;
import com.cat.miao.R;
import com.cat.miao.model.LoginBean;
import com.cat.miao.model.SignBean;
import com.cat.miao.network.RxRetrofitForSign;
import com.cat.miao.network.RxRetrotifForLogin;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        LinearLayout signUpButton = (LinearLayout) findViewById(R.id.sign_up_button);
        final EditText userName = (EditText) findViewById(R.id.signup_username_edit_text);
        final EditText mail = (EditText) findViewById(R.id.signup_mail_edit_text);
        final EditText password = (EditText) findViewById(R.id.signup_password_edit_text);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxRetrofitForSign.getInstens().getSignInfo(new RxRetrofitForSign.CallBack() {
                    @Override
                    public Map<String, String> getMap() {
                        Map<String, String> map = new HashMap<>();
                        map.put("name", userName.getText().toString());
                        map.put("password", password.getText().toString());
                        map.put("email", mail.getText().toString());
                        return map;
                    }

                    @Override
                    public void onSuccess(SignBean signBean){
                        if(signBean.getCode().equals("200")){
                            RxRetrotifForLogin.getInstens().getLoginInfo(new RxRetrotifForLogin.CallBack() {
                                @Override
                                public Map<String, String> getMap() {
                                    Map<String, String> map = new HashMap<>();
                                    map.put("password", password.getText().toString());
                                    map.put("email", mail.getText().toString());
                                    return map;
                                }

                                @Override
                                public void onSuccess(LoginBean loginBean){
                                    Log.e("error", loginBean.getMessage() );
                                }
                            });
                            Intent intent = new Intent();
                            intent.setClass(SignupActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
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
}
