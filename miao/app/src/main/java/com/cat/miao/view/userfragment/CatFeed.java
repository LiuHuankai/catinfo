package com.cat.miao.view.userfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cat.miao.*;
import com.cat.miao.model.FeedBean;
import com.cat.miao.model.FeedUpdateBean;
import com.cat.miao.network.RxRetrofitForFeed;

import at.markushi.ui.CircleButton;

public class CatFeed extends AppCompatActivity {
    Integer feedId;
    Integer feedTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_feed);

        Intent intent = getIntent();
        feedId = intent.getIntExtra("id", 1);
        final TextView toolBarTitle = (TextView) findViewById(R.id.toobar_title);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        initView();

        //初始化toolbar
        toolBarTitle.setText("投喂");
        toolbar.setTitle("  ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initView(){
        final TextView feedPlaceText = (TextView) findViewById(R.id.feedplace_text_view);
        final TextView lastFeedTimeText = (TextView) findViewById(R.id.lastfeedtime_text_view);
        final TextView feedTimesText = (TextView) findViewById(R.id.feedtime_text_view);
        final CircleButton feedButton = (CircleButton) findViewById(R.id.feed_button);

        RxRetrofitForFeed.getInstens().getFeedInfo(new RxRetrofitForFeed.CallBack() {
            @Override
            public void onSuccess(FeedBean feedBean) {
                feedPlaceText.setText(feedBean.getResult().getLocation());
                lastFeedTimeText.setText(feedBean.getResult().getGmtModified());
                feedTimes = feedBean.getResult().getTimes();
                feedTimesText.setText("次数  " + feedTimes.toString());
            }
        }, feedId);

        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feed();
                feedTimesText.setText("次数  " + feedTimes.toString());
            }
        });
    }

    public void feed(){
        final TextView feedTimesText = (TextView) findViewById(R.id.feedtime_text_view);

        if(feedTimes < 3){
            RxRetrofitForFeed.getInstens().getUpdateInfo(new RxRetrofitForFeed.UpdateCallBack() {
                @Override
                public void onSuccess(FeedUpdateBean feedUpdateBean) {
                    if(feedUpdateBean.getCode().equals("200") && feedUpdateBean!= null){
                        feedTimes += 1;
                        feedTimesText.setText("次数  " + feedTimes.toString());
                    }else{
                        Toast.makeText(CatFeed.this, "投喂失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }, feedId);
        }else{
            Toast.makeText(CatFeed.this, "今日投喂次数已经达到上限", Toast.LENGTH_SHORT).show();
        }
    }
}
