package com.cat.miao.view.userfragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cat.miao.R;
import com.cat.miao.model.UserInfoBean;
import com.cat.miao.network.RxRetrofitForUserInfo;
import com.cat.miao.view.LoginActivity;
import com.cat.miao.view.LogoutActivity;
import com.cat.miao.view.SignupActivity;

public class UserFragment extends Fragment {
    public static UserFragment getInstance(){
        UserFragment userFragment = new UserFragment();
        return userFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.e("error", "在这里");
        View view = inflater.inflate(R.layout.user_fragment, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_avatar);
        TextView userNameTextView = (TextView) view.findViewById(R.id.tv_username);
        TextView toolBarTitle = (TextView)view.findViewById(R.id.toobar_title);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        Button userInfoButton = (Button) view.findViewById(R.id.myinfo);
        Button aboutUsButton = (Button) view.findViewById(R.id.aboutus);
        Button feedCat = (Button) view.findViewById(R.id.feedcat);
        Button catTrack = (Button) view.findViewById(R.id.track);

        //如果在登陆状态，则加载用户头像以及名字
        SharedPreferences sp = getActivity().getSharedPreferences("sp_user_state", Context.MODE_PRIVATE);
        final String state = sp.getString("login_state", "default");
        final String userName = sp.getString("username", "default");
        final String headImage = sp.getString("headImage", "default");
        if(state.equals("1")){
            Glide.with(getActivity())
                    .load("http://q2wxpbxdw.bkt.clouddn.com/" + headImage)
                    .into(imageView);

            userNameTextView.setText(userName);
        }

        //为头像设置点击事件
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取登陆状态
                SharedPreferences sp = getActivity().getSharedPreferences("sp_user_state", Context.MODE_PRIVATE);
                String status = sp.getString("login_state", "2");

                if(! status.equals("default")){
                    if(status.equals("2")){
                        Intent intent = new Intent(getActivity(), SignupActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(getActivity(), LogoutActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        //为用户信息按钮设置点击事件
        userInfoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SharedPreferences sp = getActivity().getSharedPreferences("sp_user_state", Context.MODE_PRIVATE);
                String status = sp.getString("login_state", "default");

                if(status.equals("1"))
                {
                    Intent intent = new Intent(getActivity(), UserInfo.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(), "请先登陆", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //为投喂按钮设置点击事件
        feedCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NFCRecognize.class);
                startActivity(intent);
            }
        });

        //为猫咪踪迹设置点击事件
        catTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CatTrack.class);
                startActivity(intent);
            }
        });


        //为页面上方toolbar设定标题
        toolBarTitle.setText("我的");
        toolbar.setTitle("  ");

        return view;
    }
}
