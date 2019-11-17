package com.cat.miao.view.userfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cat.miao.R;

public class UserFragment extends Fragment {

    public static UserFragment getInstance(){
        UserFragment userFragment = new UserFragment();
        return userFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.e("error", "你是傻逼");
        View view = inflater.inflate(R.layout.user_fragment, container, false);

        return view;
    }

}
