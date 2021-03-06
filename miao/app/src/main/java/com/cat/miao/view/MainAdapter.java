package com.cat.miao.view;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cat.miao.view.AdoptFragment.AdoptFragment;
import com.cat.miao.view.catfragment.CatInfoFragment;
import com.cat.miao.view.userfragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();

    public MainAdapter(FragmentManager fm) {

        super(fm);

        fragments.add(CatInfoFragment.getInstance());

        fragments.add(AdoptFragment.getInstance());

        fragments.add(UserFragment.getInstance());

    }

    @Override
    public Fragment getItem(int position){
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
