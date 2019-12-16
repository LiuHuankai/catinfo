package com.cat.miao.view.userfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cat.miao.R;
import com.cat.miao.model.UserInfoUpdateBean;
import com.cat.miao.network.RxRetrofitForUserInfo;

import org.w3c.dom.Text;

import java.util.Map;

public class UserInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        TextView toolBarTitle = (TextView) findViewById(R.id.toobar_title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolBarTitle.setText("用户信息");

        toolbar.setTitle("  ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        updata();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
