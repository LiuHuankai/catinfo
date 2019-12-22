package com.cat.miao.view.AdoptFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.cat.miao.model.AdoptPostBean;
import com.cat.miao.model.CatBean;
import com.cat.miao.network.RxRetrofitForAdopt;
import com.cat.miao.network.RxRetrofitForCat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdoptInfoActivity extends AppCompatActivity{

    CatBean.Result catinfoResult = new CatBean().getResult();
    ArrayList<AdoptBean.Result> infoResult = new ArrayList<AdoptBean.Result>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt_info);

        TextView toolBarTitle = (TextView) findViewById(R.id.toobar_title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        final ImageView img = (ImageView) findViewById(R.id.adopt_image);
        final TextView name = (TextView) findViewById(R.id.adopt_name);
        final ImageButton button = (ImageButton) findViewById(R.id.comment_button);
        final EditText newBlog = (EditText) findViewById(R.id.blog_textview);

        int id;

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

        final int num1 = this.getIntent().getIntExtra("adoptid",1);

        SharedPreferences sp= getSharedPreferences("sp_user_state", Context.MODE_PRIVATE);
        final String userid=sp.getString("id","1");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("更新blog", "别按了");
                //Toast.makeText(CatInfoActivity.this, "申请成功???", Toast.LENGTH_SHORT).show();
//                if(infoResult == null){
//
//                }
//                else{
//                    RxRetrofitForAdopt.getInstens().pushblogs(new RxRetrofitForAdopt.PushCallBack() {
//                        @Override
//                        public void onSuccess(AdoptPushBean adoptPushBean) {
//                            Log.e("push成功了吗？", adoptPushBean.getCode());
//                            if(adoptPushBean.getCode().equals("200")){
//                                Toast.makeText(AdoptInfoActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
//                                update();
//                            }
//                            else if(adoptPushBean.getCode().equals("401")){
//                                Toast.makeText(AdoptInfoActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
//                            }
//                            else{
//                                Log.e("失败了", adoptPushBean.getCode());
//                                Toast.makeText(AdoptInfoActivity.this, "更新失败！", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public Map<String, String> getMap() {
//                            Map<String, String> map = new HashMap<>();
//                            map.put("adopterId", userid);
//                            map.put("blog", newBlog.getText().toString());
//                            map.put("catId", ""+catinfoResult.getID());
//                            map.put("id", ""+catinfoResult.getID());
//
//                            Log.e("adopterId", userid);
//                            Log.e("catId", ""+catinfoResult.getID());
//                            Log.e("blog", "blog"+newBlog.getText().toString());
//                            return map;
//                        }
//                    });
//                }


                RxRetrofitForAdopt.getInstens().postblogs(new RxRetrofitForAdopt.PostCallback() {
                    @Override
                    public void onSuccess(AdoptPostBean adoptPostBean) {
                        Log.e("post成功了吗？", adoptPostBean.getCode());
                        if(adoptPostBean.getCode().equals("200")){
                            Toast.makeText(AdoptInfoActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
                            update();
                        }
                        else if(adoptPostBean.getCode().equals("401")){
                            Toast.makeText(AdoptInfoActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Log.e("失败了", adoptPostBean.getCode());
                            Toast.makeText(AdoptInfoActivity.this, "更新失败！", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public Map<String, String> getMap() {
                        Map<String, String> map = new HashMap<>();
                        map.put("adopterId", userid);
                        map.put("blog", newBlog.getText().toString());
                        map.put("catId", ""+catinfoResult.getID());
                        map.put("id", ""+catinfoResult.getID());

                        return map;
                    }
                });
            }
        });

        RxRetrofitForCat.getInstens().getEveryCat(new RxRetrofitForCat.CallBack() {
            @Override
            public void onSuccess(CatBean catBean) {
//                Log.e( "testAdoptadopt",catBean.getCode());
                catinfoResult = catBean.getResult();
                Log.e( "catinfoResult: ", ""+catinfoResult.getID());
                if(catinfoResult.getName()!=null){
                    name.setText(catinfoResult.getName());
                }
                else{
                    name.setText("暂缺");
                }
                Glide.with(AdoptInfoActivity.this).load(catinfoResult.getUrl()).into(img);


            }

            @Override
            public void onError() {

            }
        },num1);

        update();
    }

    public void update(){
        Log.e( "update: ", "我在update里了！");
        RxRetrofitForAdopt.getInstens().getblogs(new RxRetrofitForAdopt.CallBack() {
            @Override
            public void onSuccess(AdoptBean adoptBean) {
                final TextView blog = (TextView) findViewById(R.id.adopt_blog);
                Log.e( "testAdoptblog","我在success里了！");
                if(adoptBean.getCode().equals("200")){
//                    Log.e( "infoResult.size",""+infoResult.size());

//                    int m=0;
//                    infoResult = adoptBean.getResult();
//                    for(int i=infoResult.size()-1;i>=0;i++){
//                        Log.e( "catinfoResult.getID()",""+catinfoResult.getID());
//                        Log.e( "get(i).getCatId()",""+i+infoResult.get(i).getCatId());
//                        if(infoResult.get(i).getCatId() == catinfoResult.getID()){
//                            blog.setText(infoResult.get(i).getBlog());
//                            m=1;
//                            break;
//                        }
//                    }
//                    if(m==0){
//                        blog.setText("您还没有发表过博客");
//                    }
                    infoResult = adoptBean.getResult();
                    blog.setText(infoResult.get(infoResult.size()-1).getBlog());
                }
                else if(adoptBean.getCode().equals("404")){
                    blog.setText(adoptBean.getMessage());
                }
                else{
                    blog.setText("请先登录!");
                }

            }

            @Override
            public void onError() {
                Log.e( "testAdoptblog","这不是乱死？");
            }
        });
    }
}
