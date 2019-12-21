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

    private String[] titles = {//

            "第一页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度",//

            "第二页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度",//

            "第三页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度", //

            "第四页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度"};

    public MainAdapter(FragmentManager fm) {

        super(fm);

//        fragments.add(CatFragment.getInstance());

        fragments.add(CatInfoFragment.getInstance());

//        fragments.add(TextFragment.newInstance(titles[1]));

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
