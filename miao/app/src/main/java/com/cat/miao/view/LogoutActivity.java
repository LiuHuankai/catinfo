package com.cat.miao.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.cat.miao.*;
import com.cat.miao.model.LogoutBean;
import com.cat.miao.network.RxRetrofitForLogout;

import java.util.HashMap;
import java.util.Map;

public class LogoutActivity extends AppCompatActivity {
    Integer choose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        LinearLayout logoutButton = (LinearLayout) findViewById(R.id.logout_button);
        LinearLayout changeButton = (LinearLayout) findViewById(R.id.change_account_button);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            //登出账号
            @Override
            public void onClick(View v) {
                //如果登出成功则跳转到主界面
                choose = 1;
                logout();
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            //切换账号
            @Override
            public void onClick(View v) {
                //如果登出成功则跳转到登陆界面
                choose = 2;
                logout();
            }
        });

        initView();
    }

    private void initView(){

    }

    private void logout(){
        //向后端发送通知，用户已登出
        SharedPreferences sp = getSharedPreferences("sp_user_state", Context.MODE_PRIVATE);
        final String account = sp.getString("account", "default");
        RxRetrofitForLogout.getInstens().getLogoutInfo(new RxRetrofitForLogout.CallBack() {
            @Override
            public Map<String, String> getMap() {
                    //获得用户账号得到登出依据
                    Map<String, String> map = new HashMap<>();
                    map.put("email", account);
                    return map;
            }

            @Override
            public void onSuccess(LogoutBean logoutBean) {
                //将用户的登陆状态改为登出,并且将登陆账号移除,其中2代表登出
                SharedPreferences sp = getSharedPreferences("sp_user_state", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("login_state", "2");
                editor.remove("account");
                editor.apply();

                //跳转
                Intent intent = new Intent();
                if(choose == 1){
                    intent.setClass(LogoutActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    intent.setClass(LogoutActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                //输出登出成功信息
                Log.e("logout", logoutBean.getMessage());
            }
        });
    }
}
