package com.cat.miao.view.catfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.cat.miao.R;
import com.cat.miao.model.CatBean;
import com.cat.miao.model.ReplyBean;
import com.cat.miao.network.RxRetrofitForCat;
import com.cat.miao.network.RxRetrofitForReply;
import com.cat.miao.view.LoginActivity;

import java.util.HashMap;
import java.util.Map;

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

        final int num1 = this.getIntent().getIntExtra("catid",1);


        final ImageView img = (ImageView) findViewById(R.id.cat_info_img);
        final TextView name = (TextView) findViewById(R.id.cat_info_name);
        final TextView gender = (TextView) findViewById(R.id.cat_info_gender);
        final TextView color = (TextView) findViewById(R.id.cat_info_color);
        final TextView kind = (TextView) findViewById(R.id.cat_info_kind);
        final TextView location = (TextView) findViewById(R.id.cat_info_location);
        final TextView remarks = (TextView) findViewById(R.id.cat_info_remarks);
        final Button button = (Button)findViewById(R.id.cat_adopt_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("error", "别按了");
                //Toast.makeText(CatInfoActivity.this, "申请成功???", Toast.LENGTH_SHORT).show();
                RxRetrofitForReply.getInstens().postAdoptReply(new RxRetrofitForReply.CallBack() {
                    @Override
                    public void onSuccess(ReplyBean replyBean) {
                        Log.e("成功了吗？", replyBean.getCode());
                        if(replyBean.getCode().equals("200")){
                            Log.e("error", "成功了吗？");
                            Toast.makeText(CatInfoActivity.this, "申请成功！", Toast.LENGTH_SHORT).show();
                        }
                        else if(replyBean.getCode().equals("401")){
                            Toast.makeText(CatInfoActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
                        }
                        else if(replyBean.getCode().equals("500")){
                            Toast.makeText(CatInfoActivity.this, "格式错误！", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(CatInfoActivity.this, "申请失败！", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public Map<String, String> getMap() {
                        Map<String, String> map = new HashMap<>();
                        map.put("catId", ""+infoResult.getID());
                        map.put("examinedId", ""+infoResult.getID());
                        map.put("Id", ""+infoResult.getID());
                        map.put("reason", "333555");
                        map.put("status", "1");
                        map.put("userId", "1");

                        Log.e("error", ""+infoResult.getID());
                        Log.e("status", "1");
                        return map;
                    }
                });
            }
        });

        RxRetrofitForCat.getInstens().getEveryCat(new RxRetrofitForCat.CallBack() {
            @Override
            public void onSuccess(CatBean catBean) {
                Log.e( "testCat",catBean.getCode());
//                Log.e( "testCatURL",catBean.getResult().getUrl());
                infoResult = catBean.getResult();
                name.setText(infoResult.getName());
                color.setText(infoResult.getColor());
                kind.setText(infoResult.getKind());
                location.setText(infoResult.getLocation());
                gender.setText(infoResult.getGender());
                remarks.setText(infoResult.getRemarks());
                Log.e( "id+UUURRRRLLLL: ", num1+infoResult.getUrl());
                Glide.with(CatInfoActivity.this).load(infoResult.getUrl()).into(img);
            }

            @Override
            public void onError() {

            }
        },num1);
//        Log.e("信息界面的URL: ", infoResult.getUrl());

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
