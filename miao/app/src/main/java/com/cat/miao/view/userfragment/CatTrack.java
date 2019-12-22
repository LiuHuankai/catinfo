package com.cat.miao.view.userfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.cat.miao.*;
import com.cat.miao.util.hotmaputil.SvgUtil;
import com.cat.miao.util.hotmaputil.hotmapmodel.TjMapModel;
import com.cat.miao.view.htmap.ChinaMapView;

public class CatTrack extends AppCompatActivity {
    private ChinaMapView mapview;
    private TjMapModel myMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_track);


    }

    private void initMap() {
        //拿到SVG文件，解析成对象
        myMap = new SvgUtil(this).getProvinces();

        //传数据
        mapview.setMap(myMap);
    }
}
