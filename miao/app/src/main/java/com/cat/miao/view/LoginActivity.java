package com.cat.miao.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cat.miao.MainActivity;
import com.cat.miao.R;
import com.cat.miao.model.LoginBean;
import com.cat.miao.model.UserInfoUpdateBean;
import com.cat.miao.network.RxRetrofitForUserInfo;
import com.cat.miao.network.RxRetrotifForLogin;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        final LinearLayout loginButton = (LinearLayout) findViewById(R.id.login_button);
        final EditText mailOrPhone = (EditText) findViewById(R.id.input_nickname_edit_text);
        final EditText password = (EditText) findViewById(R.id.input_password_edit_text);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxRetrotifForLogin.getInstens().getLoginInfo(new RxRetrotifForLogin.CallBack() {
                    @Override
                    public Map<String, String> getMap() {
                        Map<String, String> map = new HashMap<>();
                        map.put("password", password.getText().toString());
                        map.put("email", mailOrPhone.getText().toString());
                        Log.e("error", password.getText().toString());
                        Log.e("error", mailOrPhone.getText().toString());
                        return map;
                    }

                    @Override
                    public void onSuccess(LoginBean loginBean){
                        AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);

                        updata();

                        if(loginBean.getCode().equals("200")){
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, MainActivity.class);

                            //使用SharedPreference在本地存储用户的登陆状态，其中1代表已登陆；
                            SharedPreferences sp = getSharedPreferences("sp_user_state", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("login_state", "1");
                            editor.putString("account", mailOrPhone.getText().toString());
                            editor.apply();

                            //登陆成功，跳转到猫咪界面
                            startActivity(intent);
                        }
                        else if(loginBean.getCode().equals("400")){
                            Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                        }
                        else if(loginBean.getCode().equals("404")){
                            Toast.makeText(LoginActivity.this, "该用户不存在", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
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

    private void updata(){
        RxRetrofitForUserInfo.getInstens().updateInfo(new RxRetrofitForUserInfo.UpdateCallBack() {
            @Override
            public Map<String, String> getMap() {
                return null;
            }

            @Override
            public void onSuccess(UserInfoUpdateBean userInfoUpdateBean) {
                Log.e("error", userInfoUpdateBean.getMessage() );
            }
        });
    }
}
