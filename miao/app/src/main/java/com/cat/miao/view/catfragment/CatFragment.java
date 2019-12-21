package com.cat.miao.view.catfragment;

import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cat.miao.R;
import com.cat.miao.hotmapmodel.TjMapModel;
import com.cat.miao.util.ColorChangeUtil;
import com.cat.miao.view.htmap.ColorView;
import com.cat.miao.util.SvgUtil;
import com.cat.miao.view.htmap.ChinaMapView;
import com.cat.miao.hotmapmodel.MycolorArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class CatFragment extends Fragment {
    private ChinaMapView mapview;
    private TjMapModel myMap;
    private Button changeType;
    private ColorView colorView;
    private HashMap<String, List<MycolorArea>> colorView_hashmap;

    public static CatFragment getInstance(){
        CatFragment catFragment = new CatFragment();
        return catFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.cat_fragment, container, false);

        mapview = (ChinaMapView) view.findViewById(R.id.view);
        colorView = (ColorView) view.findViewById(R.id.colorView);

        setColorView();

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

    private void setColorView() {
        colorView_hashmap = new HashMap<>();
        for (int i = 0; i < ColorChangeUtil.nameStrings.length; i++) {
            String colors[] = ColorChangeUtil.colorStrings[i].split(",");
            String texts[] = ColorChangeUtil.textStrings[i].split(",");
            List<MycolorArea> list = new ArrayList<>();
            for (int j = 0; j < colors.length; j++) {
                MycolorArea c = new MycolorArea();
                c.setColor(Color.parseColor(colors[j]));
                c.setText(texts[j]);
                list.add(c);
            }
            colorView_hashmap.put(ColorChangeUtil.nameStrings[i], list);
        }
        colorView.setList(colorView_hashmap.get(ColorChangeUtil.nameStrings[0]));
    }

    private void initMap() {
        //拿到SVG文件，解析成对象
        myMap = new SvgUtil(getActivity()).getProvinces();

        //传数据
        mapview.setMap(myMap);
    }
}
