package com.cat.miao.view.AdoptFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.cat.miao.R;
import com.cat.miao.model.AdoptBean;
import com.cat.miao.model.AdoptPushBean;
import com.cat.miao.model.CatBean;
import com.cat.miao.network.RxRetrofitForAdopt;
import com.cat.miao.network.RxRetrofitForCat;

import java.util.HashMap;
import java.util.Map;

public class AdoptInfoActivity extends AppCompatActivity{

    CatBean.Result catinfoResult;
    AdoptBean.Result infoResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt_info);

        TextView toolBarTitle = (TextView) findViewById(R.id.toobar_title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolBarTitle.setText("领养信息");

        toolbar.setTitle("  ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
/*
        final int num1 = this.getIntent().getIntExtra("adoptid",1);

        SharedPreferences sp= getSharedPreferences("data", Context.MODE_PRIVATE);
        final String userid=sp.getString("id","1");

        final ImageView img = (ImageView) findViewById(R.id.adopt_image);
        final TextView name = (TextView) findViewById(R.id.adopt_name);
        final TextView blog = (TextView) findViewById(R.id.adopt_blog);
        final ImageButton button = (ImageButton) findViewById(R.id.comment_button);
        final EditText newBlog = (EditText) findViewById(R.id.blog_textview);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("error", "别按了");
                //Toast.makeText(CatInfoActivity.this, "申请成功???", Toast.LENGTH_SHORT).show();
                RxRetrofitForAdopt.getInstens().postblogs(new RxRetrofitForAdopt.PushCallBack() {
                    @Override
                    public void onSuccess(AdoptPushBean adoptPushBean) {
                        Log.e("成功了吗？", adoptPushBean.getCode());
                        if(adoptPushBean.getCode().equals("200")){
                            Toast.makeText(AdoptInfoActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
                            update();
                        }
                        else if(adoptPushBean.getCode().equals("401")){
                            Toast.makeText(AdoptInfoActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(AdoptInfoActivity.this, "更新失败！", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public Map<String, String> getMap() {
                        Map<String, String> map = new HashMap<>();
                        map.put("adopterId", userid);
                        map.put("blog", newBlog.getText().toString());
                        map.put("catId", ""+catinfoResult.getID());
                        map.put("id", "1");

                        Log.e("catId", ""+catinfoResult.getID());
                        Log.e("blog", newBlog.getText().toString());
                        return map;
                    }
                });
            }
        });

        RxRetrofitForCat.getInstens().getEveryCat(new RxRetrofitForCat.CallBack() {
            @Override
            public void onSuccess(CatBean catBean) {
                Log.e( "testAdoptadopt",catBean.getCode());
                catinfoResult = catBean.getResult();
                name.setText(catinfoResult.getName());
                Glide.with(AdoptInfoActivity.this).load(catinfoResult.getUrl()).into(img);
            }

            @Override
            public void onError() {

            }
        },num1);

        update();
    }

    public void update(){
        RxRetrofitForAdopt.getInstens().getblogs(new RxRetrofitForAdopt.CallBack() {
            @Override
            public void onSuccess(AdoptBean adoptBean) {
                Log.e( "testAdoptblog",adoptBean.getCode());
                Log.e( "testAdoptblog","内容："+infoResult.getBlog());
//                infoResult = adoptBean.getResult();
//                blog.setText(infoResult.getBlog());
            }

            @Override
            public void onError() {

            }
        });*/
    }
}
