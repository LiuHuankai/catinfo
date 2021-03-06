package com.cat.miao.view.userfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cat.miao.*;
import com.cat.miao.util.hotmaputil.ColorChangeUtil;
import com.cat.miao.util.hotmaputil.SvgUtil;
import com.cat.miao.util.hotmaputil.hotmapmodel.TjMapModel;
import com.cat.miao.view.htmap.TjMapView;

public class CatTrack extends AppCompatActivity {
    private TjMapView mapview;
    private TjMapModel myMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_track);

        TextView toolBarTitle = (TextView) findViewById(R.id.toobar_title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mapview = (TjMapView) findViewById(R.id.view);

        //初始化热图,并且设置热图事件
        initMap();
        ColorChangeUtil.changeMapColors(myMap, ColorChangeUtil.nameStrings[0]);
        mapview.chingeMapColors();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initMap() {
        //拿到SVG文件，解析成对象
        myMap = new SvgUtil(this).getAreas();

        //传数据
        mapview.setMap(myMap);
    }
}
