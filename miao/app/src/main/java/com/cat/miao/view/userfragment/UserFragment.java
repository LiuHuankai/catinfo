package com.cat.miao.view.userfragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.cat.miao.R;
import com.cat.miao.SignupActivity;

import org.w3c.dom.Text;

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
        TextView toolBarTitle = (TextView)view.findViewById(R.id.toobar_title);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        Button userInfoButton = (Button) view.findViewById(R.id.myinfo);

        Button aboutUsButton = (Button) view.findViewById(R.id.aboutus);

        //为头像设置点击事件
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignupActivity.class);
                startActivity(intent);
            }
        });
        //为页面上方toolbar设定标题
        toolBarTitle.setText("我的");
        toolbar.setTitle("  ");
        //为用户信息按钮设置点击事件
        userInfoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), UserInfo.class);
                startActivity(intent);
            }
        });

        //为关于我们按钮设置点击事件
        aboutUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutUs.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
