package com.cat.miao.view.catfragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cat.miao.R;
import com.cat.miao.util.hotmaputil.hotmapmodel.TjMapModel;
import com.cat.miao.util.hotmaputil.ColorChangeUtil;
import com.cat.miao.util.hotmaputil.SvgUtil;
import com.cat.miao.view.htmap.ChinaMapView;

public class CatFragment extends Fragment {
    private ChinaMapView mapview;
    private TjMapModel myMap;

    public static CatFragment getInstance(){
        CatFragment catFragment = new CatFragment();
        return catFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.cat_fragment, container, false);

        mapview = (ChinaMapView) view.findViewById(R.id.view);

        initMap();

        ColorChangeUtil.changeMapColors(myMap, ColorChangeUtil.nameStrings[0]);
        mapview.chingeMapColors();
        mapview.setOnChoseProvince(new ChinaMapView.onProvinceClickLisener(){
            @Override
            public void onChose(String provincename) {
            }
        });

        return view;
    }

    private void initMap() {
        //拿到SVG文件，解析成对象
        myMap = new SvgUtil(getActivity()).getProvinces();

        //传数据
        mapview.setMap(myMap);
    }
}
