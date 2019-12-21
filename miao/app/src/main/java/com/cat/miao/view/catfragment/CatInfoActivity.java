package com.cat.miao.view.catfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cat.miao.R;
import com.cat.miao.model.CatBean;
import com.cat.miao.network.RxRetrofitForCat;

public class CatInfoActivity extends AppCompatActivity {

    CatBean.Result infoResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_info);

        TextView toolBarTitle = (TextView) findViewById(R.id.toobar_title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolBarTitle.setText("猫咪信息");

        toolbar.setTitle("  ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int num1 = this.getIntent().getIntExtra("catid",1);


        final ImageView img = (ImageView) findViewById(R.id.cat_info_img);
        final TextView name = (TextView) findViewById(R.id.cat_info_name);
        final TextView gender = (TextView) findViewById(R.id.cat_info_gender);
        final TextView color = (TextView) findViewById(R.id.cat_info_color);
        final TextView kind = (TextView) findViewById(R.id.cat_info_kind);
        final TextView location = (TextView) findViewById(R.id.cat_info_location);
        final TextView remarks = (TextView) findViewById(R.id.cat_info_remarks);

        RxRetrofitForCat.getInstens().getEveryCat(new RxRetrofitForCat.CallBack() {
            @Override
            public void onSuccess(CatBean catBean) {
                Log.e( "testCat",catBean.getCode());

                infoResult = catBean.getResult();
                name.setText(infoResult.getName());
                color.setText(infoResult.getColor());
                kind.setText(infoResult.getKind());
                location.setText(infoResult.getLocation());
                gender.setText(infoResult.getGender());
                remarks.setText(infoResult.getRemarks());

            }

            @Override
            public void onError() {

            }
        },num1);
//        Glide.with(this).load(infoResult.getUrl()).into(img);
//        Glide.with(this).load("http://img1.imgtn.bdimg.com/it/u=697661107,2424028308&fm=26&gp=0.jpg").into(img);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
