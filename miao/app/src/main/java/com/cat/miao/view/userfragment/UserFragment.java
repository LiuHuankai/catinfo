package com.cat.miao.view.userfragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.cat.miao.LoginActivity;
import com.cat.miao.MainActivity;
import com.cat.miao.R;
import com.cat.miao.SignupActivity;

public class UserFragment extends Fragment {
    public static UserFragment getInstance(){
        UserFragment userFragment = new UserFragment();
        return userFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.e("error", "你是傻逼");
        View view = inflater.inflate(R.layout.user_fragment, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.iv_avatar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignupActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
